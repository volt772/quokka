package com.kakao.quokka.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * BaseViewHolder
 */
abstract class BaseViewHolder<D, out B : ViewDataBinding>(parent: ViewGroup, @LayoutRes layoutRes: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)) {

    protected val context: Context = itemView.context

    protected val binding: B = DataBindingUtil.bind(itemView)!!

    open fun recycled() {}
}
