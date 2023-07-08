package com.kakao.quokka.ui.search

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kakao.domain.constants.QkdResourceType
import com.kakao.quokka.constants.QkConstants.Pref.FAVORITE_KEY
import com.kakao.quokka.constants.QkConstants.Pref.HISTORY_KEY
import com.kakao.quokka.ext.currMillis
import com.kakao.quokka.ext.setOnSingleClickListener
import com.kakao.quokka.ext.visibilityExt
import com.kakao.quokka.model.DocumentDto
import com.kakao.quokka.model.HistoryModel
import com.kakao.quokka.preference.PrefManager
import com.kakao.quokka.preference.stringSetLiveData
import com.kakao.quokka.ui.adapter.DocumentLoadStateAdapter
import com.kakao.quokka.ui.adapter.DocumentsAdapter
import com.kakao.quokka.ui.base.BaseFragment
import com.kakao.quokka.ui.dashboard.DashBoardActivity
import com.kako.quokka.BR
import com.kako.quokka.R
import com.kako.quokka.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * SearchFragment
 * @desc Tab '검색'
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    @Inject lateinit var prefManager: PrefManager

    private val vm: SearchViewModel by viewModels()

    private var queryKeyword: String = ""   // Current Search Keyword
    private val historyModels: MutableList<HistoryModel> = mutableListOf()  // History Keywords

    private lateinit var docAdapter: DocumentsAdapter   // Search Result(=Document) Adapter

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        initView()
        subscribers()
        initDataSet()
        viewForEmptyDocuments(true)
    }

    /**
     * Initialize View
     * @desc Search, Adapter
     */
    private fun initView() {
        with(binding) {
            /* Search Dialog*/
            ivSearch.setOnSingleClickListener {
                val categoryListDialog = SearchDialog.newInstance(
                    historyModels,
                    ::doSearch,
                    ::delHistories,
                    ::clearHistories
                )
                requireActivity()
                    .supportFragmentManager
                    .beginTransaction()
                    .add(categoryListDialog, TAG)
                    .commitAllowingStateLoss()
            }

            /* Adapter*/
            docAdapter = DocumentsAdapter(::doFavorite).apply {
                stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
                rvDocs.apply {
                    setHasFixedSize(true)
                    adapter = withLoadStateFooter(
                        footer = DocumentLoadStateAdapter { retry() }
                    )
                }

                addLoadStateListener { loadState ->
                    if (loadState.append.endOfPaginationReached) {
                        viewForEmptyDocuments(true)
                    }

                    if (loadState.refresh is LoadState.Loading) {
                        progressBar.visibilityExt(true)
                    } else {
                        progressBar.visibilityExt(false)

                        val errorState = when {
                            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                            else -> null
                        }

                        errorState?.let { error ->
                            Toast.makeText(requireActivity(), error.error.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    /**
     * Initialize Data Set
     */
    private fun initDataSet() {
        /* History (Recent Keyword)*/
        viewLifecycleOwner.lifecycleScope.launch { vm.getHistories() }
    }

    /**
     * Initialize Subscribers
     */
    private fun subscribers() {
        /* Listen SharedPreference Change (key='favorite'))*/
        val prefs = prefManager.preferences
        val stringPrefLiveData = prefs.stringSetLiveData(FAVORITE_KEY, setOf())
        stringPrefLiveData.observe(viewLifecycleOwner) { prf ->
            val currFragment = (activity as DashBoardActivity).activeFragment
            if (currFragment != this) {
                viewLifecycleOwner.lifecycleScope.launch {
                    vm.queryDocuments(queryKeyword)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.run {
            /* Search By Keyword*/
            launch {
                vm.query.collectLatest { _query ->
                    collectUiState(_query)
                }
            }

            /* Recent History Keyword*/
            launch {
                vm.history.collectLatest { histories ->
                    historyModels.addAll(histories)
                }
            }
        }
    }

    /**
     * Execute Search
     * @param query Search Keyword
     * @desc Search & Save Keyword to Preference
     */
    private fun doSearch(query: String) {
        lifecycleScope.launch {

            var updatedModel: HistoryModel?= null
            query.let { _query ->
                historyModels.forEach { h ->
                    prefManager.run {
                        /* keyword Exist : Only Update Datetime*/
                        if (h.keyword == query) {
                            updateStringSet(HISTORY_KEY, query)
                            updatedModel = h
                        } else {
                            /* keyword Not Exist : Add*/
                            addStringSet(HISTORY_KEY, _query)
                        }
                    }
                }

                updatedModel?.let { um -> historyModels.remove(um) }

                historyModels.add(0, HistoryModel(_query, currMillis))

                queryKeyword = _query
                vm.queryDocuments(_query)
            }
        }
    }

    /**
     * Delete History Item
     */
    private fun delHistories() {
        val updated = mutableSetOf<String>().also { _s ->
            historyModels.forEach { _history ->
                val value = "${_history.keyword}||${_history.regDate}"
                _s.add(value)
            }
        }
        prefManager.setStringSet(HISTORY_KEY, updated)
    }

    /**
     * Clear History Items
     * @desc Delete key 'history'
     */
    private fun clearHistories() {
        prefManager.clearKey(HISTORY_KEY)
    }

    /**
     * Mark Favorite
     * @param doc Selected Document Dto
     */
    private fun doFavorite(doc: DocumentDto, position: Int) {
        val favoriteState = !doc.isFavorite
        doc.isFavorite = favoriteState
        docAdapter.notifyItemChanged(position)

        val url = if (doc.type == QkdResourceType.IMAGE) {
            doc.thumbnailUrl
        } else {
            doc.thumbnail
        }

        prefManager.run {
            if (favoriteState) addStringSet(FAVORITE_KEY, url)
            else removeStringSet(FAVORITE_KEY, url)
        }
    }

    /**
     * List Submit Data
     * @desc Render Paging List by API Response
     */
    private fun collectUiState(query: String = "") {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getDocuments(query).collectLatest { docs ->
                viewForEmptyDocuments(false)
                docAdapter.submitData(docs)
            }
        }
    }

    /**
     * Branch off view between paging list and empty view
     */
    private fun viewForEmptyDocuments(visible: Boolean) {
        val infoMsg = if (queryKeyword.isBlank()) {
            getString(R.string.empty_keyword)
        } else {
            getString(R.string.empty_result)
        }

        binding.apply {
            clNoDocs.visibilityExt(visible)
            tvNoDocs.text = infoMsg
        }
    }

    companion object {
        const val TAG = "SearchFragment"
    }
}