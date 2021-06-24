package com.example.mobilliumtask.view.home

import android.app.AlertDialog
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.mobilliumtask.R
import com.example.mobilliumtask.adapter.*
import com.example.mobilliumtask.databinding.FragmHomeBinding
import com.example.mobilliumtask.model.*
import com.example.mobilliumtask.model.Collection
import com.example.mobilliumtask.utils.CustomPageTransformer
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeFragmentBinding: FragmHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    private val productAdapter = ProductAdapter(CardType.HOME)
    private val categoryAdapter = CategoryAdapter()
    private val collectionAdapter = CollectionAdapter(CardType.HOME)
    private var featureAdapter: FeatureAdapter? = null
    private var editorShopAdapter: EditorShopAdapter? = null
    private var newShopAdapter: NewShopAdapter? = null

    private var editorShopCoverImages: List<String>? = null
    private var productList: List<Product>? = null
    private var collectionList: List<Collection>? = null
    private var editorShopList: List<Shop>? = null
    private var newShopList: List<Shop>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeFragmentBinding = FragmHomeBinding.inflate(inflater, container, false)
        return homeFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        initRecyclerViews()

        initErrorLiveData()
        initListDataLiveData()
        initUIStateLiveData()

        initViewPagerPageListener()
        initSwipeRefreshLayout()
        initUIButtons()
    }

    private fun initUIButtons() {
        homeFragmentBinding.txtProductSeeAll.setOnClickListener { view ->
            val bundle: Bundle = bundleOf("productList" to productList)
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_productListFragment, bundle)
        }
        homeFragmentBinding.txtCollectionSeeAll.setOnClickListener { view ->
            val bundle: Bundle = bundleOf("collectionList" to collectionList)
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_collectionListFragment, bundle)
        }
        homeFragmentBinding.txtEditorShopSeeAll.setOnClickListener { view ->
            val bundle: Bundle = bundleOf("shopList" to editorShopList)
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_shopListFragment, bundle)
        }
        homeFragmentBinding.txtNewShopSeeAll.setOnClickListener { view ->
            val bundle: Bundle = bundleOf("shopList" to newShopList)
            view.findNavController()
                .navigate(R.id.action_homeFragment_to_shopListFragment, bundle)
        }


    }

    private fun initErrorLiveData() {
        homeViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(this@HomeFragment.context)
                .setTitle(R.string.error)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
        })
    }

    private fun initListDataLiveData() {
        homeViewModel.listDataLiveData.observe(viewLifecycleOwner, Observer {

            productList = it[1].products
            collectionList = it[3].collections
            editorShopCoverImages = it[4].shops?.map { it.cover?.url.toString() }
            editorShopList = it[4].shops
            newShopList = it[5].shops

            submitListIntoAdapter(it)
            writeUITitles(it)


        })
    }

    private fun writeUITitles(list: List<ListData>) {
        list[1].title?.let {
            homeFragmentBinding.txtProductTitle.text = it
        }
        list[2].title?.let {
            homeFragmentBinding.txtCategoryTitle.text = it
        }
        list[3].title?.let {
            homeFragmentBinding.txtCollectionTitle.text = it
        }
        list[4].title?.let {
            homeFragmentBinding.txtEditorShopTitle.text = it
        }
        list[5].title?.let {
            homeFragmentBinding.txtNewShopTitle.text = it
        }

    }

    private fun initRecyclerViews() {
        homeFragmentBinding.recyclerProduct.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFragmentBinding.recyclerProduct.setHasFixedSize(true)
        homeFragmentBinding.recyclerProduct.adapter = productAdapter

        homeFragmentBinding.recyclerCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFragmentBinding.recyclerCategory.setHasFixedSize(true)
        homeFragmentBinding.recyclerCategory.adapter = categoryAdapter

        homeFragmentBinding.recyclerCollection.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        homeFragmentBinding.recyclerCollection.setHasFixedSize(true)
        homeFragmentBinding.recyclerCollection.adapter = collectionAdapter

        homeFragmentBinding.viewPagerFeatured.adapter = featureAdapter
        homeFragmentBinding.viewPagerEditorShop.adapter = editorShopAdapter
        homeFragmentBinding.viewPagerNewShop.adapter = newShopAdapter

    }

    private fun initViewPager() {
        featureAdapter = FeatureAdapter(requireContext())
        editorShopAdapter = EditorShopAdapter(requireContext())
        newShopAdapter = NewShopAdapter(requireContext(), CardType.HOME)
    }

    private fun submitListIntoAdapter(list: List<ListData>) {
        featureAdapter?.submitList(list[0].featured ?: listOf())
        homeFragmentBinding.tabIndicator.setViewPager(homeFragmentBinding.viewPagerFeatured)
        homeFragmentBinding.viewPagerFeatured.setPageTransformer(false, CustomPageTransformer())

        productAdapter.submitList(list[1].products ?: listOf())
        categoryAdapter.submitList(list[2].categories ?: listOf())
        collectionAdapter.submitList(list[3].collections ?: listOf())

        editorShopAdapter?.submitList(list[4].shops ?: listOf())
        Picasso.get().load(editorShopCoverImages?.get(0))
            .into(homeFragmentBinding.editorShopBackground)
        context?.getColor(R.color.transparent_dark_gray)
            ?.let { homeFragmentBinding.editorShopBackground.setColorFilter(it) }

        newShopAdapter?.submitList(list[5].shops ?: listOf())


    }

    private fun initUIStateLiveData() {
        homeViewModel.uistateLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                UIState.LIVE -> liveState()
                UIState.LOADING -> loadingState()
            }
        })
    }

    private fun liveState() {
        homeFragmentBinding.constraintHome.visibility = View.VISIBLE
        homeFragmentBinding.homeSwipeRefresh.isRefreshing = false
    }

    private fun loadingState() {
        homeFragmentBinding.constraintHome.visibility = View.INVISIBLE
        homeFragmentBinding.homeSwipeRefresh.isRefreshing = true
    }

    private fun initViewPagerPageListener() {
        homeFragmentBinding.viewPagerEditorShop.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                Picasso.get().load(editorShopCoverImages?.get(position))
                    .noPlaceholder()
                    .into(homeFragmentBinding.editorShopBackground)
                context?.getColor(R.color.transparent_dark_gray)
                    ?.let {
                        val colorMatrix = ColorMatrix()
                        colorMatrix.setSaturation(0f)
                        homeFragmentBinding.editorShopBackground.colorFilter = ColorMatrixColorFilter(colorMatrix)
                    }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun initSwipeRefreshLayout() {
        homeFragmentBinding.homeSwipeRefresh.setOnRefreshListener {
            homeViewModel.getDataFromRemote()
            homeFragmentBinding.constraintHome.visibility = View.INVISIBLE
        }

    }
}