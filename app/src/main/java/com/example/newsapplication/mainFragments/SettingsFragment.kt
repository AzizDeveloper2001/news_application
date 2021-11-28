package com.example.newsapplication.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentSettingsBinding
import com.example.newsapplication.utils.MySharedPreference
import com.example.newsapplication.utils.ThemeHelper
import com.mahfa.dnswitch.DayNightSwitchListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class SettingsFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentSettingsBinding
    lateinit var mySharedPreference:MySharedPreference
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
            binding.apply {
                mySharedPreference = MySharedPreference.getinstance(requireContext())

                if (mySharedPreference.getsharedpreference("isDark") == "1") {
                    binding.mod.text=resources.getString(R.string.dark)
                    ThemeHelper.applyTheme(ThemeHelper.darkMode)
                    binding.daynight.setIsNight(true,true)
                } else {
                    binding.mod.text=resources.getString(R.string.mode)
                    ThemeHelper.applyTheme(ThemeHelper.lightMode)
                    binding.daynight.setIsNight(false,false)
                }
                daynight.setListener(DayNightSwitchListener { is_night ->

                    if (is_night) {
                        mySharedPreference.putsharedprefence("isDark", "1")
                        binding.mod.text=resources.getString(R.string.dark)
                        ThemeHelper.applyTheme(ThemeHelper.darkMode)
                    } else{
                        mySharedPreference.putsharedprefence("isDark", "0")
                        binding.mod.text=resources.getString(R.string.mode)
                        ThemeHelper.applyTheme(ThemeHelper.lightMode)
                    }
                })
            }
            binding.next.setOnClickListener {
                findNavController().navigate(R.id.languageFragment,null, navOptions {
                    anim {
                        enter=android.R.anim.fade_in
                        exit=android.R.anim.fade_out
                    }
                })
            }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}