package com.example.mobilliumtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.CardCollectionBinding
import com.example.mobilliumtask.model.CardType
import com.example.mobilliumtask.model.Collection
import com.squareup.picasso.Picasso

class CollectionAdapter(private val cardType: CardType) :
    RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    private val collectionList: MutableList<Collection> = mutableListOf()
    private lateinit var collectionCardBinding: CardCollectionBinding
    private var lastPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CollectionAdapter.CollectionViewHolder {
        collectionCardBinding =
            CardCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CollectionViewHolder(collectionCardBinding)
    }

    override fun onBindViewHolder(holder: CollectionAdapter.CollectionViewHolder, position: Int) {
        holder.bind(collectionList[position])
        setAnimation(holder.itemView, position)

    }

    override fun getItemCount(): Int {
        return collectionList.size
    }

    inner class CollectionViewHolder(private val binding: CardCollectionBinding) :
        RecyclerView.ViewHolder(collectionCardBinding.root) {
        fun bind(collectionItem: Collection) {

            when (cardType) {
                CardType.DETAIL -> {
                    binding.cardCollection.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    binding.cardCollection.requestLayout()
                }
                CardType.HOME -> {
                    //Default
                }
            }

            collectionItem.cover?.url?.let {
                Picasso.get().load(it).noPlaceholder().into(binding.imgProduct)
            }
            binding.txtCollectionTitle.text = collectionItem.title
            binding.txtCollectionDefinition.text = collectionItem.definition


        }
    }

    fun submitList(newList: List<Collection>) {
        collectionList.clear()
        collectionList.addAll(newList)
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