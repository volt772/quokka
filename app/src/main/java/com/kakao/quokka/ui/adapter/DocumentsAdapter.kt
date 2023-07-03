package com.kakao.quokka.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kakao.domain.dto.QkDocuments
import com.kakao.domain.dto.QkdDocuments
import com.kako.quokka.databinding.ItemDocumentsBinding

class DocumentsAdapter : PagingDataAdapter<QkDocuments, DocumentsViewHolder>(DocumentsDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentsViewHolder {
        return DocumentsViewHolder(
            ItemDocumentsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DocumentsViewHolder, position: Int) {
        holder.bind(getItem(position)?.datetime)
    }
}

class DocumentsDiffCallBack : DiffUtil.ItemCallback<QkDocuments>() {
    override fun areItemsTheSame(oldItem: QkDocuments, newItem: QkDocuments): Boolean {
        return oldItem.datetime == newItem.datetime
    }

    override fun areContentsTheSame(oldItem: QkDocuments, newItem: QkDocuments): Boolean {
        return oldItem == newItem
    }
}

class DocumentsViewHolder(
    val binding:ItemDocumentsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(path: String?) {
        path?.let {
//            binding.ivMoviePoster.load("https://image.tmdb.org/t/p/w500/$it") {
//                crossfade(durationMillis = 2000)
//                transformations(RoundedCornersTransformation(12.5f))
//            }
        }
    }
}