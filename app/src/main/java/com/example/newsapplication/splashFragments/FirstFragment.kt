package com.example.newsapplication.splashFragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.App
import com.example.newsapplication.MainActivity
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentFirstBinding
import com.example.newsapplication.utils.MySharedPreference
import com.example.newsapplication.viewmodels.NewsViewModels
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class FirstFragment : Fragment() {
       lateinit var binding:FragmentFirstBinding
       @Inject
       lateinit var newsViewModels: NewsViewModels
       lateinit var mySharedPreference: MySharedPreference
 
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFirstBinding.inflate(inflater,container,false)
        App.appComponent.first(this)
        mySharedPreference= MySharedPreference.getinstance(requireContext())
        val countDownTimer=object :CountDownTimer(1000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                        if(mySharedPreference.getsharedpreference("category")=="category"){
                            val intent=Intent(requireActivity(),MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            findNavController().navigate(R.id.secondFragment,null, navOptions {
                                anim {
                                    enter=R.anim.left_to_right
                                    exit=R.anim.right_to_left


                                }
                            })
                }


            }

        }
        countDownTimer.start()
        return binding.root
    }

}