package com.kakao.quokka.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kakao.domain.dto.QkDocuments
import com.kakao.quokka.QuokkaApp.Companion.appContext
import com.kakao.quokka.ext.convertFormat
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
        holder.bind(getItem(position))
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

    fun bind(doc: QkDocuments?) {
        doc?.let { _doc ->
            val thumbUrl = _doc.thumbnailUrl.ifBlank {
                _doc.thumbnail
            }

            Glide.with(appContext)
                .load(thumbUrl)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.bitmapTransform(
                    MultiTransformation(CenterCrop(), RoundedCorners(12))
                ))
                .into(binding.ivThumbnail)

            val dateTime = _doc.datetime.convertFormat()
            println("probe :: datetime : $dateTime")
            binding.apply {
                tvDate.text = dateTime.first
                tvTime.text = dateTime.second
            }

        }
    }
}