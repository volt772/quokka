package com.kakao.quokka.ui.cabinet

import androidx.fragment.app.viewModels
import com.kakao.quokka.ui.DashBoardViewModel
import com.kakao.quokka.ui.base.BaseFragment
import com.kakao.quokka.ui.search.SearchFragment
import com.kako.quokka.BR
import com.kako.quokka.R
import com.kako.quokka.databinding.FragmentCabinetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CabinetFragment : BaseFragment<FragmentCabinetBinding>(R.layout.fragment_cabinet) {

    private val vm: DashBoardViewModel by viewModels()

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
    }

//    private var _binding: FragmentHomeBinding? = null
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
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    companion object {
        fun newInstance() = CabinetFragment().apply {
//            this.selectedDate = selectedDate this.isDueAllDay = isDueAllDay
//            param = Unit
        }
    }
}