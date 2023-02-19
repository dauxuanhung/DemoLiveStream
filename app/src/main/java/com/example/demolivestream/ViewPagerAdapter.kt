package com.example.demolivestream

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter( val listData:ArrayList<ChanelData>,val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun createFragment(position: Int): Fragment {
        return FragmentStream.getInstance(listData[position])
    }
}