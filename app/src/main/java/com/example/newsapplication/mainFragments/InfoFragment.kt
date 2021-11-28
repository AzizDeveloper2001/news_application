package com.example.newsapplication.mainFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.App
import com.example.newsapplication.R
import com.example.newsapplication.database.Entity.BookMarkEntity
import com.example.newsapplication.databinding.FragmentInfoBinding
import com.example.newsapplication.models.ArticlesItem
import com.example.newsapplication.viewmodels.NewsViewModels
import com.like.LikeButton
import com.like.OnLikeListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class InfoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @Inject
    lateinit var newsViewModels: NewsViewModels
    lateinit var binding:FragmentInfoBinding
    lateinit var articlesItem: ArticlesItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentInfoBinding.inflate(inflater,container,false)
        articlesItem=arguments?.getSerializable("article") as ArticlesItem
        App.appComponent.info(this)
        val bookMarkEntity=BookMarkEntity(articlesItem.publishedAt,articlesItem.author,articlesItem.urlToImage,
        articlesItem.description,articlesItem.source,articlesItem.title,articlesItem.url,articlesItem.content)
        binding.apply {
            lifecycleScope.launch {
                newsViewModels.getAllSavedArticles().collect{
                    if(it.contains(bookMarkEntity)){
                        save.isLiked=true
                    }
                }
            }
            Picasso.get().load(articlesItem.urlToImage).placeholder(R.drawable.placeholder).into(image)
            title.text=articlesItem.title
            for (i in 0..5){
                desc.text=desc.text.toString()+"\n${articlesItem.description}"
            }
            if(articlesItem.author!=null){
                if(articlesItem.author.toString().length>8){
                    singletitle.text=articlesItem.author.toString().substring(0,7)+"..."
                } else {
                    singletitle.text = articlesItem.author
                }
            }

            back.setOnClickListener{
                findNavController().popBackStack()
            }
            share.setOnClickListener {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, articlesItem.url)

                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }


        }
        binding.save.setOnLikeListener(object :OnLikeListener{
            override fun liked(likeButton: LikeButton?) {
                newsViewModels.addSavedArticles(bookMarkEntity)
            }

            override fun unLiked(likeButton: LikeButton?) {
                newsViewModels.deleteSavedArticles(bookMarkEntity)

            }

        })
        binding.root.setTransitionListener(object:MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                binding.save.setLikeDrawableRes(R.drawable.lik_black)
                binding.save.setUnlikeDrawableRes(R.drawable.s_black)
            }


            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                if(motionLayout?.currentState==motionLayout?.endState){
                    binding.save.setLikeDrawableRes(R.drawable.lik_black)
                    binding.save.setUnlikeDrawableRes(R.drawable.s_black)
                    binding.image.cornerRadius=20f
                    binding.title.setTextColor(resources.getColor(R.color.titlecolors))
                } else {
                binding.save.setLikeDrawableRes(R.drawable.lik)
                binding.save.setUnlikeDrawableRes(R.drawable.s_white)
                    binding.image.cornerRadius=0f
                    binding.title.setTextColor(Color.parseColor("#ffffff"))
            }}

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }

        })
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}