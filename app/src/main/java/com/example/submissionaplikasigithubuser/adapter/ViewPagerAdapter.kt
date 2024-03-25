package com.example.submissionaplikasigithubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submissionaplikasigithubuser.ui.fragment.FollowersFragment
import com.example.submissionaplikasigithubuser.ui.fragment.FollowingsFragment

class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FollowersFragment()
            1 -> FollowingsFragment()
            else -> throw IllegalArgumentException("Invalid Position")
        }
    }
}
