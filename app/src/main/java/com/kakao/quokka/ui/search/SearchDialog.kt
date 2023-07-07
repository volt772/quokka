package com.kakao.quokka.ui.search

import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kakao.quokka.ext.setOnSingleClickListener
import com.kakao.quokka.ext.visibilityExt
import com.kakao.quokka.model.HistoryModel
import com.kakao.quokka.ui.adapter.HistoryAdapter
import com.kakao.quokka.ui.base.BaseBottomSheetDialog
import com.kako.quokka.R
import com.kako.quokka.databinding.DialogSearchBinding


class SearchDialog : BaseBottomSheetDialog<DialogSearchBinding, Unit>(
    R.layout.dialog_search,
    R.style.BottomDialogRoundStyle
) {

    private var histories: MutableList<HistoryModel> = mutableListOf()  // History Keywords
    private var doSearch: ((String) -> Unit)? = null    // Func() -> Search
    private var delHistories: (() -> Unit)? = null      // Func() -> Delete keyword
    private var clearHistories: (() -> Unit)? = null    // Func() -> Clear keyword list

    private val deleteTargetList: MutableList<HistoryModel> = mutableListOf()   // Delete Targets
    private var dismissFlag: DismissFlag = DismissFlag.DELETE

    private lateinit var historyAdapter: HistoryAdapter

    override fun onStart() {
        super.onStart()
        dialog?.also { dlg ->
            val bottomSheet = dlg.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = resources.displayMetrics.heightPixels
            view?.requestLayout()
        }

        viewForEmpty()
    }

    /**
     * Branch off view between search view and empty view
     */
    private fun viewForEmpty() {
        binding.apply {
            clSearchBotEmpty.visibilityExt(histories.isEmpty())
            clSearchBot.visibilityExt(histories.isNotEmpty())
        }
    }

    /**
     * Render Dialog
     */
    override fun prepareDialog(param: Unit) {
        binding.apply {
            /* Go back (Dismiss the Dialog)*/
            ivBack.setOnSingleClickListener { dismiss() }

            /* Clear All*/
            tvClearHistories.setOnSingleClickListener { dialogClearHistory() }

            /* List Adapter*/
            historyAdapter = HistoryAdapter(::dialogSearch, ::dialogDeleteHistory)
            rvHistories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = historyAdapter
            }

            historyAdapter.submitList(histories)

            /* Search By Keywrod*/
            svDocs.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { _query ->
                        dialogSearch(_query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    /**
     * Execute Search
     * @desc Dismiss dialog and save keyword to fragment's flow value named 'query'
     */
    private fun dialogSearch(query: String) {
        dismissFlag = DismissFlag.QUERY
        doSearch?.invoke(query)
        dismiss()
    }

    /**
     * Delete Keyword
     * @desc just add to 'deleteTargetList' and remove when dialog dismissed
     */
    private fun dialogDeleteHistory(history: HistoryModel) {
        dismissFlag = DismissFlag.DELETE
        deleteTargetList.add(history)
        histories.remove(history)

        viewForEmpty()
        historyAdapter.notifyDataSetChanged()
    }

    /**
     * Clear All Keywords
     * @desc remove 'history' key in SharedPrefernece
     */
    private fun dialogClearHistory() {
        dismissFlag = DismissFlag.CLEAR

        histories.clear()
        historyAdapter.notifyDataSetChanged()

        viewForEmpty()
        clearHistories?.invoke()
    }

    /**
     * Dismiss Dialog
     * @desc Delete keyword in 'deleteTargetList'
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (dismissFlag == DismissFlag.DELETE) {
            delHistories?.invoke()
        }
        deleteTargetList.clear()
    }

    companion object {
        enum class DismissFlag { QUERY, DELETE, CLEAR }
        fun newInstance(
            histories: MutableList<HistoryModel>,
            doSearch: (String) -> Unit,
            delHistories: () -> Unit,
            clearHistories: () -> Unit
        ) = SearchDialog().apply {
            this.histories = histories
            this.doSearch = doSearch
            this.delHistories = delHistories
            this.clearHistories = clearHistories
            param = Unit
        }
    }
}
