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


/**
 * CabinetFragment
 * @desc Tab '보관'
 */
@AndroidEntryPoint
class CabinetFragment : BaseFragment<FragmentCabinetBinding>(R.layout.fragment_cabinet) {

    @Inject lateinit var prefManager: PrefManager

    private val vm: CabinetViewModel by viewModels()

    private lateinit var cabinetAdapter: CabinetAdapter     // Favorites (=Cabinet) Adapter

    override fun setBindings() { binding.setVariable(BR._all, vm) }

    override fun prepareFragment() {
        initView()
        subscribers()
    }

    /**
     * Initialize View
     * @desc Adapter
     */
    private fun initView() {
        cabinetAdapter = CabinetAdapter(::selectFavorite)

        binding.rvFavors.apply {
            setHasFixedSize(true)
            adapter = cabinetAdapter
        }
    }

    /**
     * Initialize Subscribers
     */
    private fun subscribers() {
        /* Listen SharedPreference Change (key='favorite'))*/
        val prefs = prefManager.preferences
        val stringPrefLiveData = prefs.stringSetLiveData(FAVORITE_KEY, setOf())
        stringPrefLiveData.observe(viewLifecycleOwner) { prf ->
            lifecycleScope.launch {
                vm.setFavorites(prf)
            }
        }

        lifecycleScope.run {
            launch {
                /* List History Keywords*/
                vm.favorites.collect { favor ->
                    showEmptyCabinetListView(favor.count())
                    cabinetAdapter.submitList(favor)
                }
            }
        }
    }

    /**
     * Branch off view between list and empty view
     */
    private fun showEmptyCabinetListView(count: Int) {
        binding.clNoFavors.visibilityExt(count <= 0)
    }

    /**
     * Select Favorite Icon
     */
    private fun selectFavorite(c: CabinetModel) {
        prefManager.removeStringSet(FAVORITE_KEY, c.url)
    }

    companion object {
        const val TAG = "CabinetFragment"
    }
}