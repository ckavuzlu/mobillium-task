package com.example.mobilliumtask.utils

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.mobilliumtask.R

class CustomPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        if(position in -1.0..1.0){
                page.findViewById<View>(R.id.img_feature).translationX =- position * (page.width / 2)
            }
    }
}

