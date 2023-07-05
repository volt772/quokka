package com.kakao.quokka.ui.cabinet

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakao.quokka.constants.QkConstants.Pref.FAVORITE_KEY
import com.kakao.quokka.preference.QkPreference
import com.kakao.quokka.preference.stringSetLiveData
import com.kakao.quokka.ui.base.BaseFragment
import com.kako.quokka.BR
import com.kako.quokka.R
import com.kako.quokka.databinding.FragmentCabinetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CabinetFragment : BaseFragment<FragmentCabinetBinding>(R.layout.fragment_cabinet) {

    private val vm: CabinetViewModel by viewModels()

    @Inject
    lateinit var qkPreference: QkPreference

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        initView()
        subscribers()
    }

    private fun initView() {
        binding.btnTest.setOnClickListener {
//            qkPreference.delFileKey("AK01UhbEzhU")
            qkPreference.setString("notification_enabled", "cabinet!!")
        }
    }

    private fun subscribers() {
        val prefs = qkPreference.preferences
        val stringPrefLiveData = prefs.stringSetLiveData(FAVORITE_KEY, setOf())
        stringPrefLiveData.observe(viewLifecycleOwner) { enabled ->
            println("probe :: observe :: cabinet :: $enabled")
        }

        lifecycleScope.run {
            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    vm.getAllFavorites()
                }
            }

            launch {
                vm.favorites.collect {
                    println("probe :: all favors : $it")
                }
            }
        }
    }

    companion object {
        fun newInstance() = CabinetFragment().apply {
//            this.selectedDate = selectedDate this.isDueAllDay = isDueAllDay
//            param = Unit
        }
    }
}