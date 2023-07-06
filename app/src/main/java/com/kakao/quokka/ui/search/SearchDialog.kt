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

    private var histories: MutableList<HistoryModel> = mutableListOf()
    private var doSearch: ((String) -> Unit)? = null
    private var delHistories: (() -> Unit)? = null
    private var clearHistories: (() -> Unit)? = null

    private val deleteTargetList: MutableList<HistoryModel> = mutableListOf()
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

    private fun viewForEmpty() {
        binding.apply {
            clSearchBotEmpty.visibilityExt(histories.isEmpty())
            clSearchBot.visibilityExt(histories.isNotEmpty())
        }
    }

    override fun prepareDialog(param: Unit) {
        binding.apply {
            /* 뒤로가기*/
            ivBack.setOnSingleClickListener {
                dismiss()
            }

            /* 모두삭제*/
            tvClearHistories.setOnSingleClickListener {
                dialogClearHistory()
            }

            /* 최근검색리스트*/
            historyAdapter = HistoryAdapter(::dialogSearch, ::dialogDeleteHistory)
            rvHistories.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = historyAdapter
            }

            historyAdapter.submitList(histories)

            /* 검색*/
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

    private fun dialogSearch(query: String) {
        dismissFlag = DismissFlag.QUERY
        doSearch?.invoke(query)
        dismiss()
    }

    private fun dialogDeleteHistory(history: HistoryModel) {
        dismissFlag = DismissFlag.DELETE
        deleteTargetList.add(history)
        histories.remove(history)

        viewForEmpty()
        historyAdapter.notifyDataSetChanged()
    }

    private fun dialogClearHistory() {
        dismissFlag = DismissFlag.CLEAR

        histories.clear()
        historyAdapter.notifyDataSetChanged()

        viewForEmpty()
        clearHistories?.invoke()
    }

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
