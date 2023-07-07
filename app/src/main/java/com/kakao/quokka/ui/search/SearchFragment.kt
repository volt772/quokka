package com.kakao.quokka.ui.search

import android.os.Parcelable
import android.view.View
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


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val vm: SearchViewModel by viewModels()

    private lateinit var docAdapter: DocumentsAdapter
    private var queryKeyword: String = ""

    @Inject lateinit var prefManager: PrefManager
    private var recyclerViewState: Parcelable? = null

    private val historyModels: MutableList<HistoryModel> = mutableListOf()

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {

        initView()
        subscribers()
        initDataSet()
        viewForEmptyDocuments(true)
    }

    private fun initDataSet() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getHistories()
        }
    }

    private fun subscribers() {
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
            launch {
                vm.query.collectLatest { _query ->
                    collectUiState(_query)
                }
            }

            launch {
                vm.history.collectLatest { histories ->
                    historyModels.addAll(histories)
                }
            }
        }
    }

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
            docAdapter = DocumentsAdapter(::doFavorite)
            docAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT
            rvDocs.apply {
                setHasFixedSize(true)
                adapter = docAdapter.withLoadStateFooter(
                    footer = DocumentLoadStateAdapter { docAdapter.retry() }
                )
            }

            docAdapter.addLoadStateListener { loadState ->
                if (loadState.append.endOfPaginationReached) {
                    viewForEmptyDocuments(true)
                    docAdapter.itemCount == 0
                }

                if (loadState.refresh is LoadState.Loading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE

                    val errorState = when {
                        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                        else -> null
                    }

                    errorState?.let {
//                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun doSearch(query: String) {
        lifecycleScope.launch {

            var updatedModel: HistoryModel?= null
            query.let { _query ->
                historyModels.forEach { h ->
                    prefManager.run {
                        if (h.keyword == query) {
                            updateStringSet(HISTORY_KEY, query)
                            updatedModel = h
                        } else {
                            addStringSet(HISTORY_KEY, _query)
                        }
                    }
                }

                updatedModel?.let { um ->
                    historyModels.remove(um)
                }

                historyModels.add(0, HistoryModel(_query, currMillis))
                queryKeyword = _query
                vm.queryDocuments(_query)
            }
        }
    }

    private fun delHistories() {
        val updated = mutableSetOf<String>().also { _s ->
            historyModels.forEach { _history ->
                val value = "${_history.keyword}||${_history.regDate}"
                _s.add(value)
            }
        }
        prefManager.setStringSet(HISTORY_KEY, updated)
    }

    private fun clearHistories() {
        prefManager.clearKey(HISTORY_KEY)
    }

    private fun doFavorite(doc: DocumentDto, position: Int) {
        val favoriteState = !doc.isFavorite
        doc.isFavorite = favoriteState
        docAdapter.notifyItemChanged(position)

        val url = if (doc.type == QkdResourceType.IMAGE) {
            doc.thumbnailUrl
        } else {
            doc.thumbnail
        }

        if (favoriteState) {
            prefManager.addStringSet(FAVORITE_KEY, url)
        } else {
            prefManager.removeStringSet(FAVORITE_KEY, url)
        }
    }

    private fun collectUiState(query: String = "") {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getDocuments(query).collectLatest { docs ->
                viewForEmptyDocuments(false)
                docAdapter.submitData(docs)
            }
        }
    }

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