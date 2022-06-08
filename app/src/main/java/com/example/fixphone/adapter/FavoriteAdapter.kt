package com.example.fixphone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fixphone.databinding.ListitemPhoneBinding
import com.example.fixphone.model.FavoriteEntity

class FavoriteAdapter(private val onItemClick:OnClickListener): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){
    private val diffCallBack = object  : DiffUtil.ItemCallback<FavoriteEntity>(){
        override fun areItemsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return  oldItem.id_favorite == newItem.id_favorite
        }

        override fun areContentsTheSame(oldItem: FavoriteEntity, newItem: FavoriteEntity): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
    private val differ = AsyncListDiffer(this,diffCallBack)
    fun dataSumbit(value: List<FavoriteEntity>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ListitemPhoneBinding.inflate(inflater, parent, false ))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let {
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }
    inner  class ViewHolder(private  val  binding: ListitemPhoneBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteEntity){
            binding.apply {
                tvPhoneName.text = data.phoneName.toString()
                Glide.with(binding.root)
                    .load(data.Image)
                    .centerCrop()
                    .into(ivPhoneImage)
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }
    interface OnClickListener{
        fun onClickItem(data: FavoriteEntity)
    }
}