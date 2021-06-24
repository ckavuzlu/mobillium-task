package com.example.mobilliumtask.view.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobilliumtask.adapter.ProductAdapter
import com.example.mobilliumtask.databinding.FragmProductListBinding
import com.example.mobilliumtask.model.CardType
import com.example.mobilliumtask.model.Product


class ProductListFragment : Fragment() {

    private lateinit var productListBinding: FragmProductListBinding
    private val productAdapter = ProductAdapter(CardType.DETAIL)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productListBinding = FragmProductListBinding.inflate(LayoutInflater.from(context), container , false)
        return productListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getSerializable("productList")?.let {
            initProductListRecycler(it as List<Product> )
        }
    }
    fun initProductListRecycler(productList : List<Product>){
        productListBinding.recyclerProductList.layoutManager = GridLayoutManager(context, 2)
        productListBinding.recyclerProductList.setHasFixedSize(true)
        productListBinding.recyclerProductList.adapter = productAdapter
        productAdapter.submitList(productList)
    }
}