package com.example.task9new.presentation.viewPager2Entities

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.task9new.App
import com.example.task9new.presentation.fragments.HabitsListFragment
import javax.inject.Inject

private const val HABITS_LIST_ARGS = "isUseful"

class HabitsListViewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment){

    private val fragmentsList : List<HabitsListFragment> = listOf(
        HabitsListFragment().apply {
            this.arguments = Bundle().apply {
                putBoolean(HABITS_LIST_ARGS, true)
            }
        },
        HabitsListFragment().apply {
            this.arguments = Bundle().apply {
                putBoolean(HABITS_LIST_ARGS, false)
            }
        }
    )

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getItemId(position: Int): Long =
        fragmentsList[position].pageId

    override fun containsItem(itemId: Long): Boolean {
        return fragmentsList[0].pageId == itemId || fragmentsList[1].pageId == itemId
    }

}