package com.example.mobilliumtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.CardNewShopBinding
import com.example.mobilliumtask.model.Shop
import com.squareup.picasso.Picasso

class ShopListAdapter() : RecyclerView.Adapter<ShopListAdapter.ShopViewHolder>() {

    private val shopList: MutableList<Shop> = mutableListOf()
    private lateinit var shopCardBinding: CardNewShopBinding
    private var lastPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopListAdapter.ShopViewHolder {
        shopCardBinding =
            CardNewShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(shopCardBinding)
    }

    override fun onBindViewHolder(holder: ShopListAdapter.ShopViewHolder, position: Int) {
        holder.bind(shopList[position])
        setAnimation(holder.itemView, position)

    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    inner class ShopViewHolder(private val binding: CardNewShopBinding) :
        RecyclerView.ViewHolder(shopCardBinding.root) {
        fun bind(shopItem: Shop) {

            binding.txtShopTitle.text = shopItem.name
            binding.txtShopDescription.text = shopItem.definition
            binding.txtItemCount.text =
                binding.root.context.getString(R.string.item_count, shopItem.productCount)

            shopItem.cover?.url?.let {
                Picasso.get().load(it).noPlaceholder().into(binding.imgMain)
            } ?: Picasso.get().load(R.drawable.vitrinova_logo).noPlaceholder().into(binding.imgMain)

            Picasso.get().load(shopItem.logo?.url).noPlaceholder().into(binding.imgLogo)


        }
    }

    fun submitList(newList: List<Shop>) {
        shopList.clear()
        shopList.addAll(newList)
        notifyDataSetChanged()
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(
                viewToAnimate.context,
                R.anim.card_animation_right_to_left
            )
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


}