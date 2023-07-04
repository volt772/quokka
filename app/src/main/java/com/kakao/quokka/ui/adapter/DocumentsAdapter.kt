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
import com.kakao.quokka.QuokkaApp.Companion.appContext
import com.kakao.quokka.ext.convertFormat
import com.kakao.quokka.model.DocumentDto
import com.kako.quokka.databinding.ItemDocumentsBinding

class DocumentsAdapter : PagingDataAdapter<DocumentDto, DocumentsViewHolder>(DocumentsDiffCallBack()) {

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

class DocumentsDiffCallBack : DiffUtil.ItemCallback<DocumentDto>() {
    override fun areItemsTheSame(oldItem: DocumentDto, newItem: DocumentDto): Boolean {
        return oldItem.datetime == newItem.datetime
    }

    override fun areContentsTheSame(oldItem: DocumentDto, newItem: DocumentDto): Boolean {
        return oldItem == newItem
    }
}

class DocumentsViewHolder(
    val binding:ItemDocumentsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(doc: DocumentDto?) {
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
            binding.apply {
                tvDate.text = dateTime.first
                tvTime.text = dateTime.second
                tvPage.text = doc.page.toString()
            }

        }
    }
}