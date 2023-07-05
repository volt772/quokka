package com.kakao.quokka.ui.cabinet

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.quokka.constants.QkConstants.Pref.FAVORITE_KEY
import com.kakao.quokka.model.CabinetModel
import com.kakao.quokka.preference.QkPreference
import com.kakao.quokka.preference.stringSetLiveData
import com.kakao.quokka.ui.adapter.CabinetAdapter
import com.kakao.quokka.ui.adapter.DocumentsAdapter
import com.kakao.quokka.ui.base.BaseFragment
import com.kako.quokka.BR
import com.kako.quokka.R
import com.kako.quokka.databinding.FragmentCabinetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CabinetFragment : BaseFragment<FragmentCabinetBinding>(R.layout.fragment_cabinet) {

    private val vm: CabinetViewModel by viewModels()

    @Inject
    lateinit var qkPreference: QkPreference

    private lateinit var cabinetAdapter: CabinetAdapter

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        initView()
        subscribers()
    }

    private fun initView() {
//        binding.btnTest.setOnClickListener {
////            qkPreference.delFileKey("AK01UhbEzhU")
//            qkPreference.setString("notification_enabled", "cabinet!!")
//        }

        cabinetAdapter = CabinetAdapter(::selectFavorite)

        binding.rvFavors.apply {
            setHasFixedSize(true)
            adapter = cabinetAdapter
        }
    }

    private fun subscribers() {
        val prefs = qkPreference.preferences
        val stringPrefLiveData = prefs.stringSetLiveData(FAVORITE_KEY, setOf())
        stringPrefLiveData.observe(viewLifecycleOwner) { prf ->
//            println("probe :: observe :: cabinet :: ${prf.javaClass.name}")

            lifecycleScope.launch {
                vm.setFavorites(prf)
            }
        }

        lifecycleScope.run {
//            launch {
//                repeatOnLifecycle(Lifecycle.State.STARTED) {
//                    vm.getAllFavorites()
//                }
//            }

            launch {
                vm.favorites.collect { favor ->
                    cabinetAdapter.submitList(favor)
//                    println("probe :: observe :: lauch :: $it")
                }
//                vm.favorites.collect {
//                    println("probe :: all favors : $it")
//                }
            }
        }
    }

    private fun selectFavorite(c: CabinetModel) {
//        viewModel.getSelectedCategoryName(cl)
        println("probe :: adapter :$c")

        qkPreference.run {
            val favorsSet = getStringSet(FAVORITE_KEY)

            val favors = mutableSetOf<String>().also { s ->
                favorsSet.forEach { f -> s.add(f) }
            }

            favors.remove(c.url)

            qkPreference.setStringSet(FAVORITE_KEY, favors)
        }
    }

    companion object {
        fun newInstance() = CabinetFragment().apply {
//            this.selectedDate = selectedDate this.isDueAllDay = isDueAllDay
//            param = Unit
        }
    }
}