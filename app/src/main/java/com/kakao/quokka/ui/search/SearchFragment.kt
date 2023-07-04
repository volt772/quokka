package com.kakao.quokka.ui.search

import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.quokka.ext.visibilityExt
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
    private var adapter: DocumentsAdapter? = null
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
        adapter = DocumentsAdapter()
        binding.rvDocs.adapter = adapter

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
    }

    private fun collectUiState(query: String = "") {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getDocuments(query).collectLatest { docs ->
//                viewForEmptyDocuments(docs.count())
//                viewForEmptyDocuments(0)
                adapter?.submitData(docs)
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