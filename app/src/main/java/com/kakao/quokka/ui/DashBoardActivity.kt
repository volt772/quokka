package com.kakao.quokka.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kakao.quokka.ui.base.BaseActivity
import com.kakao.quokka.ui.cabinet.CabinetFragment
import com.kakao.quokka.ui.search.SearchFragment
import com.kako.quokka.R
import com.kako.quokka.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardActivity : BaseActivity<DashBoardViewModel, ActivityDashboardBinding>() {

    override val viewModel: DashBoardViewModel by viewModels()
    override fun getViewBinding(): ActivityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

    private val searchFragment = SearchFragment()
    private val cabinetFragment = CabinetFragment()
    var activeFragment: Fragment = searchFragment

    private var query: String?= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initFrag()
//        subscribers()
    }

    private fun initView() {
//        test()
    }

//    private fun test() {
//
//        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//// 2. Use the extension functions to create a LiveData object of whatever type you need and observe the result.
//        val stringPrefLiveData = prefs.stringSetLiveData("notification", setOf())
//        stringPrefLiveData.observe(this) { enabled ->
//            println("probe :: observe :: dashboard :: $enabled")
//        }

// 3. If you want to get the value associated with a particular sharedpref key and you have an object of type LiveDataSharedPreferences.kt,
//    just use the following code :
//        val boolValue = boolPrefLiveData.value
//        val stringValue = stringPrefLiveData.value
//        println("probe :: observe :: value :: $stringValue")
//    }

//    private fun subscribers() {
//        lifecycleScope.run {
//            launch {
//                viewModel.query.collect { _query ->
//                    println("probe :: subscrivers : main : query : $_query")
//                    query = _query
//                }
//            }
//        }
//    }

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
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.apply {
//                        replace(R.id.container, searchFragment)
//                        addToBackStack(null)
//                        commit()
//                    }
                    supportFragmentManager.beginTransaction().hide(activeFragment).show(searchFragment).commit()
                    activeFragment = searchFragment
                    true
                }

                R.id.nvt_cabinet -> {
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.apply {
//                        replace(R.id.container, cabinetFragment)
//                        addToBackStack(null)
//                        commit()
//                    }
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

//    private fun testSubscriber() {
//        lifecycleScope.run {
//            launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    viewModel.hamster.collect {  }
//                }
//            }
//        }
//    }
}