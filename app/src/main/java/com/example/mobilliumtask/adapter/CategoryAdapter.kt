package com.example.mobilliumtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.CardCategoryBinding
import com.example.mobilliumtask.model.Category
import com.squareup.picasso.Picasso

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categoryList: MutableList<Category> = mutableListOf()
    private lateinit var categoryCardBinding: CardCategoryBinding
    private var lastPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.CategoryViewHolder {
        categoryCardBinding =
            CardCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(categoryCardBinding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position])
        setAnimation(holder.itemView, position)

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class CategoryViewHolder(private val binding: CardCategoryBinding) :
        RecyclerView.ViewHolder(categoryCardBinding.root) {
        fun bind(categoryItem: Category) {

            categoryItem.logo?.url?.let {
                Picasso.get().load(it).noPlaceholder().into(binding.imgCategory)
            }
            binding.txtCategoryName.text = categoryItem.name

        }
    }

    fun submitList(newList: List<Category>) {
        categoryList.clear()
        categoryList.addAll(newList)
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