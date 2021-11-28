package com.example.newsapplication.splashFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentThirdBinding
import com.example.newsapplication.databinding.ItemVpBinding


class ThirdFragment : Fragment() {

lateinit var binding: FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding= FragmentThirdBinding.inflate(inflater,container,false)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.fourthFragment,null, navOptions {
                anim {
                    enter=R.anim.left_to_right
                    exit=R.anim.right_to_left


                }
            })
        }
        return binding.root
    }



}