package com.example.mobilliumtask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.CardProductBinding
import com.example.mobilliumtask.model.CardType
import com.example.mobilliumtask.model.Product
import com.example.mobilliumtask.utils.showStrikeThrough
import com.squareup.picasso.Picasso

class ProductAdapter(private val cardType: CardType) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList: MutableList<Product> = mutableListOf()
    private lateinit var productCardBinding: CardProductBinding
    private var lastPosition = -1


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        productCardBinding =
            CardProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(productCardBinding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        holder.bind(productList[position])
        setAnimation(holder.itemView, position)

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(private val binding: CardProductBinding) :
        RecyclerView.ViewHolder(productCardBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(productItem: Product) {
            when (cardType) {
                CardType.DETAIL -> {
                    binding.cardProduct.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    binding.cardProduct.requestLayout()
                }
                CardType.HOME -> {
                    //Default
                }
            }


            productItem.images?.get(0)?.url?.let {
                Picasso.get().load(it).noPlaceholder().into(binding.imgProduct)
            }
            productItem.oldPrice?.let {
                binding.txtPriceOld.text = "$it TL"
            }
            binding.txtProductName.text = productItem.title
            binding.txtProductShop.text = productItem.shop?.name
            binding.txtPriceOld.showStrikeThrough(true)
            binding.txtPrice.text = productItem.price.toString() + " TL"


        }
    }

    fun submitList(newList: List<Product>) {
        productList.clear()
        productList.addAll(newList)
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