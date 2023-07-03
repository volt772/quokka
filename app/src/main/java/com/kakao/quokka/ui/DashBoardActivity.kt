package com.kakao.quokka.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.quokka.ui.base.BaseActivity
import com.kakao.quokka.ui.cabinet.CabinetFragment
import com.kakao.quokka.ui.search.SearchFragment
import com.kako.quokka.R
import com.kako.quokka.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<DashBoardViewModel, ActivityDashboardBinding>() {

    override val viewModel: DashBoardViewModel by viewModels()
    override fun getViewBinding(): ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

    private val searchFragment = SearchFragment()
    private val cabinetFragment = CabinetFragment()
    private var activeFragment: Fragment = searchFragment

    private var query: String?= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initFrag()
        subscribers()
    }

    private fun initView() {
    }

    private fun subscribers() {
        lifecycleScope.run {
            launch {
                viewModel.query.collect { _query ->
                    println("probe :: subscrivers : main : query : $_query")
                    query = _query
                }
            }
        }
    }

    private fun initFrag() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.apply{
            add(R.id.container, searchFragment, getString(R.string.tab_tag_search)).hide(cabinetFragment)
            add(R.id.container, cabinetFragment, getString(R.string.tab_tag_cabinet))
        }.commit()

        initListeners()
    }

    private fun initListeners() {
        binding.bnNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nvt_search -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit()
                    activeFragment = searchFragment
                    true
                }

                R.id.nvt_cabinet -> {
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(cabinetFragment).commit()
                    activeFragment = cabinetFragment
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun testSubscriber() {
        lifecycleScope.run {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.hamster.collect {  }
                }
            }
        }
    }
}