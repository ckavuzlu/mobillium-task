package com.example.mobilliumtask.view.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumtask.adapter.CollectionAdapter
import com.example.mobilliumtask.databinding.FragmCollectionListBinding
import com.example.mobilliumtask.model.CardType
import com.example.mobilliumtask.model.Collection


class CollectionListFragment : Fragment() {

    private lateinit var collectionListBinding: FragmCollectionListBinding
    private val collectionAdapter = CollectionAdapter(CardType.DETAIL)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        collectionListBinding =
            FragmCollectionListBinding.inflate(LayoutInflater.from(context), container, false)
        return collectionListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getSerializable("collectionList")?.let {
            initCollectionListRecycler(it as List<Collection>)
        }
    }

    fun initCollectionListRecycler(collectionList: List<Collection>) {
        collectionListBinding.recyclerCollectionList.layoutManager = LinearLayoutManager(context)
        collectionListBinding.recyclerCollectionList.setHasFixedSize(true)
        collectionListBinding.recyclerCollectionList.adapter = collectionAdapter
        collectionAdapter.submitList(collectionList)
    }
}