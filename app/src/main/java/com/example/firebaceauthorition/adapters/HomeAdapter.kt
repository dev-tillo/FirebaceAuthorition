package com.example.firebaceauthorition.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaceauthorition.classes.User
import com.example.firebaceauthorition.databinding.ItemLayoutBinding

class HomeAdapter:
    ListAdapter<User, HomeAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User,
        ): Boolean {
            return oldItem.number == newItem.number
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: User,
            newItem: User,
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemHomeBinding.apply {
            number.text = item.number
        }
    }

    inner class Vh(var itemHomeBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {
    }
}