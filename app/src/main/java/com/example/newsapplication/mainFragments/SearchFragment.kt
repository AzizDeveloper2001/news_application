package com.example.newsapplication.mainFragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.App
import com.example.newsapplication.R
import com.example.newsapplication.adapters.RecomAdapter
import com.example.newsapplication.databinding.FragmentSearchBinding
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.utils.NewsResource
import com.example.newsapplication.viewmodels.NewsViewModels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentSearchBinding
    @Inject
    lateinit var newsViewModels: NewsViewModels
    lateinit var recomAdapter: RecomAdapter
    private  val TAG = "SearchFragment"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentSearchBinding.inflate(inflater,container,false)
        App.appComponent.search(this)
        binding.clear.setOnClickListener {
            if(binding.search.text.isNotEmpty()){
                binding.search.setText("")
            } else {
                findNavController().popBackStack()
            }
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
        binding.voice.setOnClickListener {
            val intent= Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            startActivityForResult(intent,200)
        }
        binding.rv.adapter=recomAdapter
        binding.search.requestFocus()
        val imm: InputMethodManager? =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT)
        binding.search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val tv = binding.search.text.toString()


            setsearch(tv)

                return@OnEditorActionListener true
            }
            false
        })

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==200 && resultCode== Activity.RESULT_OK){
            var arraylist= data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)



            binding.search.setText(arraylist?.get(0)!!)
            setsearch(arraylist?.get(0))







        }
    }
    fun setsearch(text:String){
        lifecycleScope.launch {
            newsViewModels.getsearchwords(binding.search.text.toString()).collect {
                when (it) {
                    is NewsResource.Success -> {
                        Log.d(TAG, "onCreateView: ${it.articlelist}")
                        recomAdapter.submitList(it.articlelist)
                        if(it.articlelist!!.isEmpty()){
                            binding.lotti.visibility = View.VISIBLE
                        } else {
                            binding.lotti.visibility = View.GONE
                        }
                        binding.bar.visibility = View.GONE
                    }
                    is NewsResource.Error -> {
                        binding.lotti.visibility = View.VISIBLE
                        binding.bar.visibility = View.GONE

                        Log.d(TAG, "error: ${it.message}")
                    }
                    is NewsResource.Loadding -> {
                        Log.d(TAG, "onCreateView: loadding")
                        binding.lotti.visibility = View.GONE
                        binding.bar.visibility = View.VISIBLE

                    }

                }
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}