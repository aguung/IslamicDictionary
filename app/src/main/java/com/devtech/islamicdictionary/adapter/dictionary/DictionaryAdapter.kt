package com.devtech.islamicdictionary.adapter.dictionary

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.devtech.islamicdictionary.data.local.entity.Dictionary
import androidx.recyclerview.widget.StaggeredGridLayoutManager





class DictionaryAdapter : PagingDataAdapter<Dictionary, DictionaryViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: DictionaryViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
//        if (payloads.isNotEmpty()) {
//            val item = getItem(position)
//        } else {
            onBindViewHolder(holder, position)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder.create(parent)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Dictionary>() {
            override fun areContentsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Dictionary, newItem: Dictionary): Boolean =
                oldItem.nomor == newItem.nomor
        }
    }
}