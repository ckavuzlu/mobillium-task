package com.example.mobilliumtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginStart
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.mobilliumtask.R
import com.example.mobilliumtask.databinding.CardNewShopBinding
import com.example.mobilliumtask.model.CardType
import com.example.mobilliumtask.model.Shop
import com.squareup.picasso.Picasso
import javax.inject.Inject

class NewShopAdapter (private val context: Context, private val cardType: CardType) : PagerAdapter() {

    private var newShopList: MutableList<Shop> = mutableListOf()
    lateinit var cardNewShopBinding: CardNewShopBinding

    override fun getCount(): Int {
        return newShopList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        cardNewShopBinding = CardNewShopBinding.inflate(LayoutInflater.from(context), container, false)

        when(cardType){
            CardType.HOME -> {
                cardNewShopBinding.btnSubscribe.visibility = View.GONE
            }
            CardType.DETAIL -> {
                cardNewShopBinding.btnSubscribe.visibility = View.VISIBLE
            }
        }

        cardNewShopBinding.txtShopTitle.text = newShopList[position].name
        cardNewShopBinding.txtShopDescription.text = newShopList[position].definition
        cardNewShopBinding.txtItemCount.text  = context.getString(R.string.item_count, newShopList[position].productCount)

        newShopList[position].cover?.url?.let {
            Picasso.get().load(it).noPlaceholder().into(cardNewShopBinding.imgMain)
        } ?: Picasso.get().load(R.drawable.vitrinova_logo).noPlaceholder().into(cardNewShopBinding.imgMain)

        newShopList[position].logo?.url?.let {
            Picasso.get().load(it).noPlaceholder().into(cardNewShopBinding.imgLogo)
        } ?: Picasso.get().load(R.drawable.vitrinova_logo).noPlaceholder().into(cardNewShopBinding.imgLogo)


        container.addView(cardNewShopBinding.root, 0)
        return cardNewShopBinding.root

    }

    fun submitList(newList: List<Shop>) {
        newShopList.clear()
        newShopList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.85F
    }

}