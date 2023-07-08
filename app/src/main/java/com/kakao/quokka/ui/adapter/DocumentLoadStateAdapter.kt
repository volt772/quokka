package com.kakao.quokka.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kakao.quokka.ext.visibilityExt
import com.kako.quokka.databinding.ItemLoadStateBinding


/**
 * DocumentLoadStateAdapter
 * @desc Document Paging Load Status
 */
class DocumentLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<DocumentLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.loadStateViewBinding.apply {
            val progress = pbLoading
            val btnRetry = btnStateRetry
            val txtErrorMessage = tvError

            btnRetry.visibilityExt(loadState !is LoadState.Loading)
            txtErrorMessage.visibilityExt(loadState !is LoadState.Loading)
            progress.visibilityExt(loadState is LoadState.Loading)

            if (loadState is LoadState.Error){
                txtErrorMessage.text = loadState.error.localizedMessage
            }

            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }

    class LoadStateViewHolder(val loadStateViewBinding: ItemLoadStateBinding) :
        RecyclerView.ViewHolder(loadStateViewBinding.root)
}