package com.example.newsapplication.mainFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentHostCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.App
import com.example.newsapplication.R
import com.example.newsapplication.adapters.MainVpAdapter
import com.example.newsapplication.adapters.RecomAdapter
import com.example.newsapplication.databinding.FragmentHomeBinding
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.utils.NewsResource
import com.example.newsapplication.viewmodels.NewsViewModels
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment(),CoroutineScope {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            App.appComponent.intialize(this)
        }
    }

    lateinit var binding:FragmentHomeBinding
    lateinit var job:Job

    @Inject
    lateinit var newsViewModels: NewsViewModels
    lateinit var mainVpAdapter: MainVpAdapter
    lateinit var recomAdapter: RecomAdapter
    private  val TAG = "HomeFragment"
    lateinit var list:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        App.appComponent.intialize(this)
        job=Job()
       binding= FragmentHomeBinding.inflate(inflater,container,false)
        loadData()
        mainVpAdapter= MainVpAdapter(list,this)
        binding.vp.isUserInputEnabled=false
        binding.vp.adapter=mainVpAdapter
        var strings=arrayListOf(R.string.business,R.string.entertainment,R.string.general,R.string.health,
            R.string.science,R.string.sport,R.string.technology)
        TabLayoutMediator(binding.tablayout, binding.vp) { tab, position -> tab.text = resources.getString(strings[position])

        }.attach()
        val tabs = binding.tablayout.getChildAt(0) as ViewGroup

        for (i in 0 until tabs.childCount ) {
            val tab = tabs.getChildAt(i)
            val layoutParams = tab.layoutParams as LinearLayout.LayoutParams
            layoutParams.weight = 0f
            layoutParams.marginEnd = 12
            layoutParams.marginStart = 12

            tab.layoutParams = layoutParams
            binding.tablayout.requestLayout()
        }


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
        setrecommended()
        binding.l.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        binding.search2.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
        return binding.root
    }

    private fun setrecommended() {
        lifecycleScope.launch {
            newsViewModels.getAllCategoies().collect {
                if(it.isNotEmpty()){
                    newsViewModels.getNewsfromNet(it[0].name).collect {
                        when(it){
                            is NewsResource.Loadding->{
                               binding.progress.visibility=View.VISIBLE
                                binding.error.visibility=View.GONE
                                binding.tvv.visibility=View.GONE
                            }
                            is NewsResource.Success->{
                               binding.progress.visibility=View.GONE
                                binding.tvv.visibility=View.VISIBLE
                                binding.error.visibility=View.GONE

                                recomAdapter.submitList(it.articlelist?.reversed())

                            }
                            is NewsResource.Error->{
                                binding.progress.visibility=View.GONE
                                binding.tvv.visibility=View.GONE
                                binding.error.visibility=View.VISIBLE
                                Toast.makeText(requireContext(), "Something is wrong, Check your internet...", Toast.LENGTH_SHORT).show()

                            }

                        }
                    }
                }
            }
        }
    }

    private fun loadData() {
        list= arrayListOf("Business","Entertainment","General","Health","Science","Sports","Technology")

    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main+job
}