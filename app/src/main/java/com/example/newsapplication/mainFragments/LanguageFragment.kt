package com.example.newsapplication.mainFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentLanguageBinding
import com.example.newsapplication.utils.MySharedPreference
import com.yariksoffice.lingver.Lingver

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LanguageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding:FragmentLanguageBinding
    lateinit var mySharedPreference: MySharedPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLanguageBinding.inflate(inflater,container,false)
        mySharedPreference=MySharedPreference.getinstance(requireContext())
        var t=mySharedPreference.getsharedpreference("lang")
        if(t!="uz"&& t!="ru" && t!="en"){
            mySharedPreference.putsharedprefence("lang","uz")
            binding.uzb.isChecked=true
        }

        binding.uzb.isChecked=(mySharedPreference.getsharedpreference("lang")=="uz")
        binding.ru.isChecked=(mySharedPreference.getsharedpreference("lang")=="ru")
        binding.eng.isChecked=(mySharedPreference.getsharedpreference("lang")=="en")
        binding.r.setOnCheckedChangeListener(object :RadioGroup.OnCheckedChangeListener{
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when(checkedId){
                    R.id.uzb->{
                        mySharedPreference.putsharedprefence("lang","uz")
                    }
                    R.id.eng->{
                                mySharedPreference.putsharedprefence("lang","en")
                    }
                    R.id.ru->{
                        mySharedPreference.putsharedprefence("lang","ru")
                    }

                }
                Lingver.getInstance().setLocale(requireContext(), mySharedPreference.getsharedpreference("lang") ?: "uz")
                binding.uzb.text = resources.getString(R.string.uz)
                binding.ru.text = resources.getString(R.string.ru)
                binding.eng.text = resources.getString(R.string.en)
                binding.lang.text=resources.getString(R.string.language)
            }

        })
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LanguageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}