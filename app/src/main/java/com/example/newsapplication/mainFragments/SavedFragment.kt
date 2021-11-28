package com.example.newsapplication.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.App
import com.example.newsapplication.R
import com.example.newsapplication.adapters.RecomAdapter
import com.example.newsapplication.databinding.FragmentCategoryBinding
import com.example.newsapplication.databinding.FragmentSavedBinding
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.viewmodels.NewsViewModels
import com.example.newsapplication.viewmodels.NewsViewModels_Factory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class SavedFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentSavedBinding
    @Inject
    lateinit var newsViewModels: NewsViewModels
    lateinit var recomAdapter: RecomAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSavedBinding.inflate(inflater,container,false)
        App.appComponent.saved(this)
        recomAdapter= RecomAdapter(object :RecomAdapter.onItemClick{
            override fun onItemClick(articlesItem: ArticlesItem) {
                val bundle=Bundle()
                bundle.putSerializable("article",articlesItem)
                findNavController().navigate(R.id.infoFragment,bundle, navOptions {
                    anim {
                        enter =android.R.anim.fade_in
                        exit=android.R.anim.fade_out
                    }
                })
            }

        })
        binding.rv.adapter=recomAdapter

        lifecycleScope.launch {
            newsViewModels.getAllSavedArticles().collect{
                var list=ArrayList<ArticlesItem>()
                it.forEach {
                    list.add(ArticlesItem(it.publishedAt,it.author,it.urlToImage,it.description,it.source,it.title,it.url,it.content))
                }
                if(list.isEmpty()){
                    binding.none.visibility=View.VISIBLE
                } else {
                    binding.none.visibility=View.GONE
                }
                recomAdapter.submitList(list)
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SavedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}