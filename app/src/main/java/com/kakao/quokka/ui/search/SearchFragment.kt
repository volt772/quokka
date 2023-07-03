package com.kakao.quokka.ui.search

import androidx.fragment.app.viewModels
import com.kakao.quokka.ui.DashBoardViewModel
import com.kakao.quokka.ui.base.BaseFragment
import com.kako.quokka.R
import com.kako.quokka.BR
import com.kako.quokka.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {

    private val vm: DashBoardViewModel by viewModels()

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        println("probe :: this frag")
//        loadingView(true)
//        ids = param.ids
//        subscribeViewModel()
//        initSectionView(!param.pushDestination.isNullOrEmpty())
//        setAdapter()
//        setUpListener()

        initView()
    }

    private fun initView() {
        vm.getTest()
        with(binding) {
//            inContent.run {
//                rvCheckList.adapter = checkListAdapter
//                srfRefresh.setOnRefreshListener {
//                    clearFilter()
//                    srfRefresh.isRefreshing = false
//                }
//            }
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