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
import com.kakao.quokka.ext.favoriteColorTint
import com.kakao.quokka.ext.setOnSingleClickListener
import com.kakao.quokka.model.DocumentDto
import com.kakao.quokka.model.DocumentModel
import com.kako.quokka.R
import com.kako.quokka.databinding.ItemDocumentsBinding
import com.kako.quokka.databinding.ItemSeparatorBinding

class DocumentsAdapter(
    private var doFavorite: ((DocumentDto, Int) -> Unit)
) : PagingDataAdapter<DocumentModel, RecyclerView.ViewHolder>(DocumentModelComparator) {

    class DocumentViewHolder(val binding: ItemDocumentsBinding): RecyclerView.ViewHolder(binding.root)
    class SeparatorViewHolder(val binding: ItemSeparatorBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_documents -> DocumentViewHolder(ItemDocumentsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> SeparatorViewHolder(ItemSeparatorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val docModel: DocumentModel = getItem(position)!!

        docModel.let {
            when (docModel) {
                is DocumentModel.DocumentItem -> {
                    val vh = holder as DocumentViewHolder
                    vh.binding.apply {
                        val _doc = docModel.doc

                        /* Thumbnail*/
                        val thumbUrl = _doc.thumbnailUrl.ifBlank { _doc.thumbnail }

                        Glide.with(appContext)
                            .load(thumbUrl)
                            .apply(RequestOptions.circleCropTransform())
                            .apply(
                                RequestOptions.bitmapTransform(
                                    MultiTransformation(CenterCrop(), RoundedCorners(12))
                                )
                            )
                            .into(ivThumbnail)

                        val dateTime = _doc.datetime.convertFormat()
                        tvDate.text = dateTime.first
                        tvTime.text = dateTime.second
                        tvType.text = _doc.key
                        tvPage.text = _doc.page.toString()

                        ivFavorite.favoriteColorTint(_doc.isFavorite)

                        ivFavorite.setOnSingleClickListener {
                            doFavorite(_doc, position)
                        }
                    }
                }

                is DocumentModel.SeparatorItem -> {
                    val vh = holder as SeparatorViewHolder
                    vh.binding.apply {
                        tvDescription.text = docModel.desc
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DocumentModel.DocumentItem -> R.layout.item_documents
            is DocumentModel.SeparatorItem -> R.layout.item_separator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    companion object {
        private val DocumentModelComparator = object : DiffUtil.ItemCallback<DocumentModel>() {
            override fun areItemsTheSame(oldItem: DocumentModel, newItem: DocumentModel): Boolean {
                return (
                    oldItem is DocumentModel.DocumentItem &&
                    newItem is DocumentModel.DocumentItem &&
                    oldItem.doc.datetime == newItem.doc.datetime) ||
                    (oldItem is DocumentModel.SeparatorItem && newItem is DocumentModel.SeparatorItem &&
                        oldItem.desc == newItem.desc)
            }

            override fun areContentsTheSame(oldItem: DocumentModel, newItem: DocumentModel): Boolean =
                oldItem == newItem
        }
    }
}