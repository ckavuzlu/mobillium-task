package com.example.mobilliumtask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.mobilliumtask.databinding.CardFeatureBinding
import com.example.mobilliumtask.model.Featured
import com.squareup.picasso.Picasso

class FeatureAdapter(private val context: Context) : PagerAdapter() {

    private var featureList: MutableList<Featured> = mutableListOf()
    lateinit var cardFeatureBinding: CardFeatureBinding
    override fun getCount(): Int {
        return featureList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        cardFeatureBinding =
            CardFeatureBinding.inflate(LayoutInflater.from(context), container, false)
        featureList[position].cover?.url?.let {
            Picasso.get().load(it).noPlaceholder().into(cardFeatureBinding.imgFeature)
        }
        cardFeatureBinding.txtFeatureTitle.text = featureList[position].title
        cardFeatureBinding.txtFeatureDefinition.text = featureList[position].subTitle

        container.addView(cardFeatureBinding.root)
        return cardFeatureBinding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    fun submitList(newList: List<Featured>) {
        featureList.clear()
        featureList.addAll(newList)
        notifyDataSetChanged()
    }


}