package com.example.mobilliumtask.view.shop


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumtask.adapter.ShopListAdapter
import com.example.mobilliumtask.databinding.FragmShopListBinding
import com.example.mobilliumtask.model.Shop


class ShopListFragment : Fragment() {

    private lateinit var shopListBinding: FragmShopListBinding
    private val shopAdapter = ShopListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        shopListBinding = FragmShopListBinding.inflate(LayoutInflater.from(context), container , false)
        return shopListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getSerializable("shopList")?.let {
            initShopListRecycler(it as List<Shop> )
        }
    }
    fun initShopListRecycler(shopList : List<Shop>){
        shopListBinding.recyclerShopList.layoutManager = LinearLayoutManager(context)
        shopListBinding.recyclerShopList.setHasFixedSize(true)
        shopListBinding.recyclerShopList.adapter = shopAdapter
        shopAdapter.submitList(shopList)
    }
}