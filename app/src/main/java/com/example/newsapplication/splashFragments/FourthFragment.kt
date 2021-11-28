package com.example.newsapplication.splashFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.App
import com.example.newsapplication.Categorymodel.CategoryModel
import com.example.newsapplication.MainActivity
import com.example.newsapplication.R
import com.example.newsapplication.adapters.CategoryRvAdapter
import com.example.newsapplication.databinding.FragmentFourthBinding
import com.example.newsapplication.utils.MySharedPreference
import com.example.newsapplication.viewmodels.NewsViewModels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import javax.inject.Inject


class FourthFragment : Fragment() {


lateinit var binding:FragmentFourthBinding
@Inject
lateinit var newsViewModels: NewsViewModels
lateinit var categoryRvAdapter: CategoryRvAdapter
lateinit var list:ArrayList<CategoryModel>
lateinit var mySharedPreference: MySharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFourthBinding.inflate(inflater,container,false)
        App.appComponent.fragmentfourth(this)
        loadData()
        mySharedPreference=MySharedPreference.getinstance(requireContext())
        categoryRvAdapter= CategoryRvAdapter(list,object :CategoryRvAdapter.onItemClick{
            override fun onAdd(categoryModel: CategoryModel) {
                categoryModel.saved=true
                newsViewModels.editCategory(categoryModel)
                mySharedPreference.putsharedprefence("category","category")

            }

            override fun onRemove(categoryModel: CategoryModel) {
                categoryModel.saved=false
               newsViewModels.editCategory(categoryModel)
                mySharedPreference.putsharedprefence("category","")
            }

        })
        binding.rv.adapter=categoryRvAdapter
        binding.apply {

                lifecycleScope.launch {

                    newsViewModels.addCategoryall(list)
                }
            next.setOnClickListener {
                        if(mySharedPreference.getsharedpreference("category")=="category") {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Toast.makeText(requireContext(), "Please, select category which you want to informed", Toast.LENGTH_SHORT).show()
                        }


            }

        }
        return binding.root
    }

    private fun loadData() {
        list= ArrayList()
        list.add(CategoryModel("Business",R.drawable.business_new,false))
        list.add(CategoryModel("Entertainment",R.drawable.entertainment_new,false))
        list.add(CategoryModel("General",R.drawable.general_new,false))
        list.add(CategoryModel("Health",R.drawable.health_new,false))
        list.add(CategoryModel("Science",R.drawable.science_new,false))
        list.add(CategoryModel("Sports",R.drawable.sport_new,false))
        list.add(CategoryModel("Technology",R.drawable.technology_new,false))

    }


}