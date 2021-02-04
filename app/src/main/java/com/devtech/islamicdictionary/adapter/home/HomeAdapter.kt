package com.devtech.islamicdictionary.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.devtech.islamicdictionary.data.local.entity.Menu
import com.devtech.islamicdictionary.databinding.ItemRvMenuBinding
import com.devtech.islamicdictionary.ui.home.HomeFragmentDirections
import javax.inject.Inject

class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeAdapter.MenuHolder>() {
    private var listGetMenu: MutableList<Menu> = ArrayList()

    fun replaceAll(items: MutableList<Menu>) {
        listGetMenu.clear()
        listGetMenu = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MenuHolder {
        val binding = ItemRvMenuBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MenuHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(listGetMenu[position], position + 1)
    }

    override fun getItemCount(): Int {
        return listGetMenu.size
    }

    inner class MenuHolder(private val binding: ItemRvMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Menu, position: Int) {
            binding.apply {
                txtMenu.text = item.nama
                when (item.posisi) {
                    0 -> txtMenu.setCompoundDrawablesWithIntrinsicBounds(item.gambar, 0, 0, 0)
                    1 -> txtMenu.setCompoundDrawablesWithIntrinsicBounds(0, item.gambar, 0, 0)
                    2 -> txtMenu.setCompoundDrawablesWithIntrinsicBounds(0, 0, item.gambar, 0)
                    3 -> txtMenu.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, item.gambar)
                }
                root.setOnClickListener {
                    root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDictionaryFragment(
                        menu = item,
                        position = position
                    ))
                }
            }
        }
    }
}