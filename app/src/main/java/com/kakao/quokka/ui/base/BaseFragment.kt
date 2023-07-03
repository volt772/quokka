package com.kakao.quokka.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {


//    protected var param: PARAM? = null

    private var _binding: B? = null
    protected val binding get() = _binding!!

    protected abstract fun setBindings()

//    protected abstract fun prepareFragment(param: PARAM)
    protected abstract fun prepareFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        performDataBinding()

//        param?.let {
            prepareFragment()
//        }
    }

    private fun performDataBinding() {
        setBindings()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
