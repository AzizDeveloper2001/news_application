package com.example.newsapplication.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapplication.mainFragments.ItemVpFragment

class MainVpAdapter(var type:List<String>,fragment:Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return type.size
    }

    override fun createFragment(position: Int): Fragment {
             return ItemVpFragment.newInstance(type[position])
    }
}