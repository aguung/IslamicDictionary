package com.devtech.islamicdictionary.adapter.dictionary

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devtech.islamicdictionary.data.local.entity.Dictionary
import com.devtech.islamicdictionary.databinding.ItemRvDictionaryBinding
import com.devtech.islamicdictionary.ui.dictionary.DictionaryFragmentDirections
import javax.inject.Inject

class DictionaryAdapter @Inject constructor() :
    PagingDataAdapter<Dictionary, DictionaryAdapter.DictionaryViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: DictionaryViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(
            ItemRvDictionaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class DictionaryViewHolder(private val binding: ItemRvDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.root.resources.configuration.locales.get(0)
        } else {
            binding.root.resources.configuration.locale
        }

        fun bind(item: Dictionary?) {
            binding.apply {
                txtName.text = item?.namaIstilah
                txtDescription.text = if (current.toString() == "in") {
                    item?.pengertianIstilahIND
                } else {
                    item?.pengertianIstilahEN
                }

                root.setOnClickListener {
                    root.findNavController().navigate(
                        DictionaryFragmentDirections.actionDictionaryFragmentToDetailDictionaryFragment(
                            dictionary = item
                        )
                    )
                }
            }
        }
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