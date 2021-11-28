package com.example.newsapplication.mainFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.App
import com.example.newsapplication.R
import com.example.newsapplication.adapters.VpRvAdapter
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.databinding.FragmentItemVpBinding
import com.example.newsapplication.databinding.ItemMainrvBinding
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.utils.NewsResource
import com.example.newsapplication.viewmodels.NewsViewModels
import com.like.LikeButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ItemVpFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentItemVpBinding
    lateinit var vpRvAdapter: VpRvAdapter
    @Inject
    lateinit var newsViewModels: NewsViewModels

    private  val TAG = "ItemVpFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.fragmentitemtype(this)
        binding= FragmentItemVpBinding.inflate(inflater,container,false)
        vpRvAdapter= VpRvAdapter(object :VpRvAdapter.onItem{
            override fun onLike(t: ArticlesItem) {
                lifecycleScope.launch {
                    val bookMarkEntity=BookMarkEntity(t.publishedAt,t.author,t.urlToImage,t.description,t.source,t.title,t.url,t.content)
                    newsViewModels.addSavedArticles(bookMarkEntity)
                }
            }

            override fun ondisLike(t: ArticlesItem) {
                lifecycleScope.launch {
                    val bookMarkEntity=BookMarkEntity(t.publishedAt,t.author,t.urlToImage,t.description,t.source,t.title,t.url,t.content)



                            newsViewModels.deleteSavedArticles(bookMarkEntity)


                }
            }

            override fun onitemclick(articlesItem: ArticlesItem) {
                val bundle=Bundle()
                bundle.putSerializable("article",articlesItem)
               findNavController().navigate(R.id.infoFragment,bundle, navOptions {
                   anim {
                       enter =android.R.anim.fade_in
                       exit=android.R.anim.fade_out
                   }
               })
            }

            override fun liked(t: ArticlesItem,likeButton: LikeButton) {
                val bookMarkEntity=BookMarkEntity(t.publishedAt,t.author,t.urlToImage,t.description,t.source,t.title,t.url,t.content)
                lifecycleScope.launch {

                    newsViewModels.getAllSavedArticles().collect{
                       likeButton.isLiked=it.contains(bookMarkEntity)
                    }
                    }

            }

        })
        binding.rvhorizontal.adapter=vpRvAdapter
        getnewsfromNet()
        return binding.root
    }

    private fun getnewsfromNet() {
        lifecycleScope.launch {

            newsViewModels.getNewsfromNet(param1!!)
                .collect{
                    when(it){
                        is NewsResource.Loadding->{
                            Log.d(TAG, "getnewsfromNet: loadding")
                        }
                        is NewsResource.Success->{
                            Log.d(TAG, "getnewsfromNet: ${it.articlelist}")
                            vpRvAdapter.submitList(it.articlelist)

                        }
                        is NewsResource.Error->{
                            Log.d(TAG, "getnewsfromNet: ${it.message}")

                        }

                    }
                }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            ItemVpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}