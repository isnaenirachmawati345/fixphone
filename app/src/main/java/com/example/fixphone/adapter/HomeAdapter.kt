package com.example.fixphone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixphone.databinding.ListitemPhoneBinding
import com.example.fixphone.model.Phone

class HomeAdapter (private val onItemClick : OnClickListener): RecyclerView.Adapter<HomeAdapter.MainViewHolder>(){

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


    inner class MainViewHolder(
        private val binding: ListitemPhoneBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Phone){
            with(binding){
                tvPhoneName.text = data.phoneName.toString()
                 tvDeskripsi.text = data.detail
                Glide.with(binding.root)
                    .load(data.image)
                    .centerCrop()
                    .into(ivPhoneImage)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MainViewHolder(ListitemPhoneBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: HomeAdapter.MainViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnClickListener{
        fun onClickItem(data: Phone)
    }
}