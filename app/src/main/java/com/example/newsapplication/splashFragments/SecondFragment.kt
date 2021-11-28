package com.example.newsapplication.splashFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.R
import com.example.newsapplication.adapters.SplashVpAdapter
import com.example.newsapplication.databinding.FragmentSecondBinding
import com.example.newsapplication.modelSplash.SplashData



class SecondFragment : Fragment() {

    lateinit var binding:FragmentSecondBinding
    lateinit var list:ArrayList<SplashData>
    lateinit var splashVpAdapter: SplashVpAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSecondBinding.inflate(inflater,container,false)
        loadData()
       splashVpAdapter= SplashVpAdapter(list)
        binding.vp.adapter=splashVpAdapter
         binding.dot.setViewPager2(binding.vp)
        binding.apply {
            vp.clipToPadding=false
            vp.clipChildren=false
            vp.offscreenPageLimit=3
            vp.getChildAt(0).overScrollMode=RecyclerView.OVER_SCROLL_NEVER
            val comp=CompositePageTransformer()
            comp.addTransformer(MarginPageTransformer(2))
            comp.addTransformer(object :ViewPager2.PageTransformer{
                override fun transformPage(page: View, position: Float) {
                    var r=1-Math.abs(position)
                    page.scaleY=0.85f+r

                }

            })
            vp.setPageTransformer(comp)
            vp.setCurrentItem(1,true)
            next.setOnClickListener {
                findNavController().navigate(R.id.thirdFragment,null, navOptions {
                    anim {
                        enter=R.anim.left_to_right
                        exit=R.anim.right_to_left


                    }
                })
            }
        }
        return binding.root
    }

    private fun loadData() {
        list= ArrayList()
        list.add(SplashData("All news in one place, be the first to know last news",R.drawable.ffirst))
        list.add(SplashData("You can find news in many languages",R.drawable.ssecond))
        list.add(SplashData("Modern study centers,take information and knowladge",R.drawable.thirdd))

    }


}