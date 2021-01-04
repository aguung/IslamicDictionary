package com.devtech.islamicdictionary.adapter.dictionary

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devtech.islamicdictionary.R
import com.devtech.islamicdictionary.data.local.entity.Dictionary
import com.devtech.islamicdictionary.ui.detail.DetailDictionaryFragment

class DictionaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.txtName)
    private val description: TextView = view.findViewById(R.id.txtDescription)
    private var items: Dictionary? = null
    private val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        view.resources.configuration.locales.get(0)
    } else {
        view.resources.configuration.locale
    }

    init {
        view.setOnClickListener {
//            MaterialAlertDialogBuilder(view.context)
//                .setTitle(items?.namaIstilah)
//                .setMessage("${items?.bahasaArab} \n ${items?.pengertianIstilahIND}")
//                .create()
//                .show()
            val data = Bundle().apply {
                putParcelable(DetailDictionaryFragment.ITEM, items)
            }
            view.findNavController().navigate(R.id.action_dictionaryFragment_to_detailDictionaryFragment, data)
        }
    }

    fun bind(item: Dictionary?) {
        this.items = item
        name.text = item?.namaIstilah
        description.text = if(current.toString() == "in"){
            item?.pengertianIstilahIND
        }else{
            item?.pengertianIstilahEN
        }
    }

    companion object {
        fun create(parent: ViewGroup): DictionaryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_dictionary, parent, false)
            return DictionaryViewHolder(view)
        }
    }
}