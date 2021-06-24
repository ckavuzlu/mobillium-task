package com.example.mobilliumtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.mobilliumtask.databinding.CardEditorShopBinding
import com.example.mobilliumtask.model.Shop
import com.squareup.picasso.Picasso

class EditorShopAdapter(private val context: Context) : PagerAdapter() {

    private var editorShopList: MutableList<Shop> = mutableListOf()
    lateinit var cardEditorShopBinding: CardEditorShopBinding

    override fun getCount(): Int {
        return editorShopList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        cardEditorShopBinding = CardEditorShopBinding.inflate(LayoutInflater.from(context), container, false)

        cardEditorShopBinding.txtCollectionTitle.text = editorShopList[position].name
        cardEditorShopBinding.txtCollectionDefinition.text = editorShopList[position].definition

        Picasso.get().load(editorShopList[position].logo?.url).noPlaceholder().into(cardEditorShopBinding.imgLogo)
        Picasso.get().load(editorShopList[position].productList?.get(0)?.images?.get(0)?.url).noPlaceholder().into(cardEditorShopBinding.imgEditorImg1)
        Picasso.get().load(editorShopList[position].productList?.get(1)?.images?.get(0)?.url).noPlaceholder().into(cardEditorShopBinding.imgEditorImg2)
        Picasso.get().load(editorShopList[position].productList?.get(2)?.images?.get(0)?.url).noPlaceholder().into(cardEditorShopBinding.imgEditorImg3)

            container.addView(cardEditorShopBinding.root, 0)
            return cardEditorShopBinding.root

    }

    fun submitList(newList: List<Shop>) {
        editorShopList.clear()
        editorShopList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.75F
    }

}