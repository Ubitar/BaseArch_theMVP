package com.common.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var fragments: List<Fragment>? = null
    var titles: List<String>? = null

    override fun getItem(position: Int): Fragment {
        return fragments!![position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles != null) {
            if (titles!!.size > position && position >= 0) {
                return titles!![position]
            }
        }
        return super.getPageTitle(position)
    }

    override fun getCount(): Int {
        return if (fragments != null) {
            fragments!!.size
        } else 0
    }


}
