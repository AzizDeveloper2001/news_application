package com.example.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemMainrvBinding
import com.example.newsapplication.databinding.ItemVpBinding

import com.example.newsapplication.models.ArticlesItem
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso


class VpRvAdapter(var listener: VpRvAdapter.onItem):ListAdapter<ArticlesItem,VpRvAdapter.Vh>(MyDiffUtill()) {
    inner class Vh(var itemRvBinding: ItemMainrvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(articlesItem: ArticlesItem) {
            Picasso.get().load(articlesItem.urlToImage).placeholder(R.drawable.placeholder).into(itemRvBinding.image)
            itemRvBinding.title.text=articlesItem.title
            listener.liked(articlesItem,itemRvBinding.save)
            itemRvBinding.save.setOnLikeListener(object :OnLikeListener{
                override fun liked(likeButton: LikeButton?) {
                       listener.onLike(articlesItem)
                }

                override fun unLiked(likeButton: LikeButton?) {
                    listener.ondisLike(articlesItem)
                }

            })
            itemRvBinding.root.setOnClickListener {
                listener.onitemclick(articlesItem)
            }

        }

    }
    class MyDiffUtill():DiffUtil.ItemCallback<ArticlesItem>(){
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
           return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMainrvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(articlesItem = getItem(position))
    }
    interface onItem{
        fun onLike(articlesItem: ArticlesItem)
        fun ondisLike(articlesItem: ArticlesItem)
        fun onitemclick(articlesItem: ArticlesItem)
        fun liked(articlesItem: ArticlesItem,likeButton: LikeButton)

    }

}