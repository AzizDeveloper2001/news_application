package com.example.newsapplication.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.App
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.R
import com.example.newsapplication.adapters.CategoryRvAdapter
import com.example.newsapplication.databinding.FragmentCategoryBinding
import com.example.newsapplication.viewmodels.NewsViewModels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoryFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentCategoryBinding
    @Inject
    lateinit var newsViewModels: NewsViewModels
    lateinit var categoryRvAdapter: CategoryRvAdapter
    lateinit var list:ArrayList<CategoryModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCategoryBinding.inflate(inflater,container,false)
        App.appComponent.category(this)
           list=ArrayList()
        categoryRvAdapter= CategoryRvAdapter(list,object :CategoryRvAdapter.onItemClick{
            override fun onAdd(categoryModel: CategoryModel) {
                           categoryModel.saved=true
                newsViewModels.editCategory(categoryModel)
            }

            override fun onRemove(categoryModel: CategoryModel) {
                            categoryModel.saved=false
                newsViewModels.editCategory(categoryModel)


            }

        })
        binding.rv.adapter=categoryRvAdapter
        lifecycleScope.launch {
            newsViewModels.getAllCategoies().collect {
                categoryRvAdapter.list=it
                categoryRvAdapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    private fun loadData() {
//        list= ArrayList()
//        list.add(CategoryModel("Business",R.drawable.business_icon))
//        list.add(CategoryModel("Entertainment",R.drawable.entertainment))
//        list.add(CategoryModel("General",R.drawable.general_ic))
//        list.add(CategoryModel("Health",R.drawable.health_ic))
//        list.add(CategoryModel("Science",R.drawable.science_ic))
//        list.add(CategoryModel("Sports",R.drawable.sport_icon))
//        list.add(CategoryModel("Technology",R.drawable.technology_icon))
//
//    }
}