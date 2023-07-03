package com.kakao.quokka.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenResumed
import com.kakao.quokka.ui.DashBoardViewModel
import com.kakao.quokka.ui.adapter.DocumentsAdapter
import com.kakao.quokka.ui.base.BaseFragment
import com.kako.quokka.R
import com.kako.quokka.BR
import com.kako.quokka.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val vm: DashBoardViewModel by viewModels()
    private var adapter: DocumentsAdapter? = null
    private var keyword: String?= null

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
//        loadingView(true)
//        ids = param.ids
//        subscribeViewModel()
//        initSectionView(!param.pushDestination.isNullOrEmpty())
//        setAdapter()
//        setUpListener()

        initView()

        collectUiState()
    }

    private fun initView() {
        adapter = DocumentsAdapter()
        binding.rvDocs.adapter = adapter

//        vm.getTest()
//        with(binding) {
////            inContent.run {
////                rvCheckList.adapter = checkListAdapter
////                srfRefresh.setOnRefreshListener {
////                    clearFilter()
////                    srfRefresh.isRefreshing = false
////                }
////            }
//        }
    }

    private fun collectUiState() {
        println("probe :: <<<<<<<<<<<<<< : keyword : $keyword")
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                vm.getDocuments().collectLatest { docs ->
//                    adapter?.submitData(docs)
//                }
//            }
//        }

//        lifecycleScope.run {
//            launch {
//                vm.getDocuments().collectLatest { docs ->
//                    adapter?.submitData(docs)
//                }
//            }
//        }
        viewLifecycleOwner.lifecycleScope.launch {
            vm.saveQueryKeyword("hamster")
            vm.getDocuments().collectLatest { docs ->
                adapter?.submitData(docs)
            }
        }
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