package com.kakao.quokka.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kakao.quokka.ext.statusBar
import com.kakao.quokka.ui.base.BaseActivity
import com.kakao.quokka.ui.cabinet.CabinetFragment
import com.kakao.quokka.ui.search.SearchFragment
import com.kako.quokka.R
import com.kako.quokka.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<DashBoardViewModel, ActivityDashboardBinding>() {

    override val vm: DashBoardViewModel by viewModels()
    override fun getViewBinding(): ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

    private val searchFragment = SearchFragment()
    private val cabinetFragment = CabinetFragment()
    var activeFragment: Fragment = searchFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFrag()
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
}