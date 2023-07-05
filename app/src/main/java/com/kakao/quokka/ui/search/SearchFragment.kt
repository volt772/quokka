package com.kakao.quokka.ui.search

import android.os.Parcelable
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kakao.domain.constants.QkdResourceType
import com.kakao.quokka.constants.QkConstants.Pref.FAVORITE_KEY
import com.kakao.quokka.ext.visibilityExt
import com.kakao.quokka.model.DocumentDto
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

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {

        initView()
        subscribers()
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
        }
    }

    private fun initView() {
        binding.rvDocs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                recyclerViewState = binding.rvDocs.getLayoutManager()?.onSaveInstanceState() // save recycleView state
            }
        })

        docAdapter = DocumentsAdapter(::doFavorite)
        docAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT


        binding.rvDocs.apply {
            setHasFixedSize(true)
            adapter = docAdapter.withLoadStateFooter(
                footer = DocumentLoadStateAdapter { docAdapter.retry() }
            )
        }

        binding.svDocs.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svDocs.clearFocus()
                lifecycleScope.launch {
                    query?.let { _query ->
                        queryKeyword = _query
                        vm.queryDocuments(_query)
                    }
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.btnRetry.setOnClickListener{
            docAdapter.retry()
        }

        docAdapter.addLoadStateListener { loadState ->

            if(loadState.append.endOfPaginationReached) {
                viewForEmptyDocuments(0)
                docAdapter.itemCount == 0
            }

            if (loadState.refresh is LoadState.Loading) {

                binding.btnRetry.visibility = View.GONE

                // Show ProgressBar
                binding.progressBar.visibility = View.VISIBLE
            }
            else {
                // Hide ProgressBar
                binding.progressBar.visibility = View.GONE

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.btnRetry.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
//                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
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
            prefManager.addDocUrl(url)
        } else {
            prefManager.removeDocUrl(url)
        }
    }

    private fun collectUiState(query: String = "") {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getDocuments(query).collectLatest { docs ->
                docAdapter.submitData(docs)
            }
        }
    }

    private fun viewForEmptyDocuments(count: Int) {
        binding.clNoDocs.visibilityExt(count <= 0)
    }

    companion object {
    }
}