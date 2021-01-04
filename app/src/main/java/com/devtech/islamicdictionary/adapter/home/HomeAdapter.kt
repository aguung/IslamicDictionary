package com.devtech.islamicdictionary.adapter.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devtech.islamicdictionary.R
import com.devtech.islamicdictionary.data.local.entity.Menu
import com.devtech.islamicdictionary.ui.dictionary.DictionaryFragment

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MenuHolder>() {
    private var listGetMenu: MutableList<Menu> = ArrayList()

    fun replaceAll(items: MutableList<Menu>) {
        listGetMenu.clear()
        listGetMenu = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_menu, viewGroup, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(listGetMenu[position], position + 1)
    }

    override fun getItemCount(): Int {
        return listGetMenu.size
    }

    inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var menu: TextView = itemView.findViewById(R.id.txtMenu)
        fun bind(item: Menu, position: Int) {
            menu.text = item.nama
            when (item.posisi) {
                0 -> menu.setCompoundDrawablesWithIntrinsicBounds(item.gambar, 0, 0, 0)
                1 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, item.gambar, 0, 0)
                2 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.gambar, 0)
                3 -> menu.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, item.gambar)
            }
            itemView.setOnClickListener {
                val data = Bundle().apply {
                    putString(DictionaryFragment.MENU, position.toString())
                    putString(DictionaryFragment.TITLE, item.nama)
                }
                itemView.findNavController().navigate(item.navigate, data)
            }

        }
    }
}