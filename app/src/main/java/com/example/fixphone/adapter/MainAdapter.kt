package com.example.fixphone.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixphone.databinding.ListitemPhoneBinding
import com.example.fixphone.model.Phone

class MainAdapter (private val onItemClick : OnClickListener): RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    private val diffCallBack = object : DiffUtil.ItemCallback<Phone>(){
        override fun areItemsTheSame(
            oldItem: Phone,
            newItem: Phone
        ): Boolean {
            return oldItem.slug == newItem.slug
        }

        override fun areContentsTheSame(
            oldItem: Phone,
            newItem: Phone
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    fun submitData(value: List<Phone>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListitemPhoneBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(private val binding: ListitemPhoneBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: Phone){
            binding.apply {
                Glide.with(binding.root)
                    .load(data.image)
                    .into(ivPhoneImage)
                tvPhoneName.text = data.phoneName
                root.setOnClickListener{
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: Phone)
    }
}