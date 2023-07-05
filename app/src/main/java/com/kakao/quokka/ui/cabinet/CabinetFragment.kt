package com.kakao.quokka.ui.cabinet

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kakao.quokka.constants.QkConstants.Pref.FAVORITE_KEY
import com.kakao.quokka.ext.visibilityExt
import com.kakao.quokka.model.CabinetModel
import com.kakao.quokka.preference.PrefManager
import com.kakao.quokka.preference.stringSetLiveData
import com.kakao.quokka.ui.adapter.CabinetAdapter
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
    lateinit var prefManager: PrefManager

    private lateinit var cabinetAdapter: CabinetAdapter

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        initView()
        subscribers()
    }

    private fun initView() {
        cabinetAdapter = CabinetAdapter(::selectFavorite)

        binding.rvFavors.apply {
            setHasFixedSize(true)
            adapter = cabinetAdapter
        }
    }

    private fun subscribers() {
        val prefs = prefManager.preferences
        val stringPrefLiveData = prefs.stringSetLiveData(FAVORITE_KEY, setOf())
        stringPrefLiveData.observe(viewLifecycleOwner) { prf ->
            lifecycleScope.launch {
                vm.setFavorites(prf)
            }
        }

        lifecycleScope.run {
            launch {
                vm.favorites.collect { favor ->
                    showEmptyCabinetListView(favor.count())
                    cabinetAdapter.submitList(favor)
                }
            }
        }
    }

    private fun showEmptyCabinetListView(count: Int) {
        binding.clNoFavors.visibilityExt(count <= 0)
    }

    private fun selectFavorite(c: CabinetModel) {
        prefManager.removeDocUrl(c.url)
    }

    companion object {
    }
}