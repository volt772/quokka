package com.kakao.quokka.ui.adapter

import android.content.res.ColorStateList
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.apx6.chipmunk.app.ui.base.BaseViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kakao.quokka.QuokkaApp
import com.kakao.quokka.ext.setOnSingleClickListener
import com.kakao.quokka.model.CabinetModel
import com.kako.quokka.R
import com.kako.quokka.databinding.ItemCabinetBinding


class CabinetAdapter(
    private val selectFavorite: (CabinetModel) -> Unit
) : ListAdapter<CabinetModel, CabinetAdapter.CabinetViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CabinetViewHolder(parent)

    override fun onBindViewHolder(holder: CabinetViewHolder, position: Int) =
        holder.bind(getItem(position), selectFavorite)


    inner class CabinetViewHolder(
        parent: ViewGroup
    ) : BaseViewHolder<CabinetModel, ItemCabinetBinding>(
        parent,
        R.layout.item_cabinet
    ) {

        fun bind(c: CabinetModel, selectFavorite: (CabinetModel) -> Unit) {

            binding.apply {
                Glide.with(QuokkaApp.appContext)
                    .load(c.url)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(
                        RequestOptions.bitmapTransform(
                            MultiTransformation(CenterCrop(), RoundedCorners(12))
                        )
                    )
                    .into(ivCabinetThumbnail)
            }

            binding.ivCabinetFavorite.setOnSingleClickListener {
                selectFavorite.invoke(c)
//                selectCheckList.invoke(cl)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CabinetModel>() {
            override fun areItemsTheSame(oldItem: CabinetModel, newItem: CabinetModel): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: CabinetModel, newItem: CabinetModel): Boolean =
                oldItem == newItem
        }
    }
}



