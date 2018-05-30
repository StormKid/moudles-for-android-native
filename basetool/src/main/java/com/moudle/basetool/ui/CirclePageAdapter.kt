package com.moudle.basetool.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.moudle.basetool.ui.BaseFragment

/**
 * Created by ke_li on 2018/1/2.
 */
class CirclePageAdapter constructor(fragmentManager: FragmentManager, private val fragmentList: MutableList<BaseFragment>) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}