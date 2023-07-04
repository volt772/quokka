package com.kakao.quokka.ui.search

import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kakao.quokka.ext.visibilityExt
import com.kakao.quokka.ui.adapter.DocumentLoadStateAdapter
import com.kakao.quokka.ui.adapter.DocumentsAdapter
import com.kakao.quokka.ui.base.BaseFragment
import com.kako.quokka.BR
import com.kako.quokka.R
import com.kako.quokka.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val vm: SearchViewModel by viewModels()
    private lateinit var docAdapter: DocumentsAdapter
    private var keyword: String?= null

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {

        initView()

        collectUiState("hamster")
        subscribers()
    }

    private fun subscribers() {
        viewLifecycleOwner.lifecycleScope.run {
            launch {
                vm.query.collectLatest { _query ->
                    collectUiState(_query)
                }
            }
        }
    }

    private fun initView() {
        docAdapter = DocumentsAdapter()

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

    private fun collectUiState(query: String = "") {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getDocuments(query).collectLatest { docs ->
//                viewForEmptyDocuments(docs.count())
//                viewForEmptyDocuments(0)
                docAdapter.submitData(docs)
            }
        }
    }

    private fun viewForEmptyDocuments(count: Int) {
        binding.clNoDocs.visibilityExt(count <= 0)
    }

    companion object {
        fun newInstance() = SearchFragment().apply {
//            this.selectedDate = selectedDate this.isDueAllDay = isDueAllDay
//            param = Unit
        }
    }

//    private var _binding: FragmentDashboardBinding? = null
//
//    // This property is only valid between onCreateView and
//    // onDestroyView.
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)
//
//        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}