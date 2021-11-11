package com.example.demoapptask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demoapptask.databinding.RecyclerViewBinding
import com.example.demoapptask.response.Data

class AdapterClass(val mainActivity: MainActivity, val data: List<Data>) :
    RecyclerView.Adapter<AdapterClass.ViewHolder>() {
    inner class ViewHolder(var binding: RecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.txtTitle.text = data.name
            binding.txtPrice.text = data.price.toString() + "$"
            binding.txtDes.text = data.product_desc
            Glide.with(mainActivity).load("https://app.bebamarket.com/"+data.img).into(binding.img)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
        val binding = RecyclerViewBinding.inflate(view)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount() = data.size
}