package com.example.task9new.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.task9new.R
import com.example.task9new.presentation.MainActivity
import com.example.task9new.presentation.viewPager2Entities.HabitsListViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_base_habit_list.*

class BaseHabitsListFragment : Fragment() {

    companion object {
        fun newInstance() : BaseHabitsListFragment = BaseHabitsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base_habit_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity

        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_burger_menu)

        // норм ли, что ут в тэблэйауте потом массив привычек фильтруется? Или это тоже лучше вынести в VM?
        val viewPagerViewPagerAdapter = HabitsListViewPagerAdapter(this.requireActivity())
        val habitsListViewPager = viewPager
        habitsListViewPager.adapter = viewPagerViewPagerAdapter

        val listsTabLayout = tabLayout
        TabLayoutMediator(listsTabLayout, habitsListViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.usefulHabitKey)
                1 -> getString(R.string.badHabitKey)
                else -> getString(R.string.errorText)
            }
        }.attach()
    }

}