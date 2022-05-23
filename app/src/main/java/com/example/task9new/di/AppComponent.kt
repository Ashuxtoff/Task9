package com.example.task9new.di

import com.example.data.di.DataModule
import com.example.data.repository.RepositoryImpl
import com.example.domain.di.DomainModule
import com.example.task9new.presentation.MainActivity
import com.example.task9new.presentation.di.PresentationModule
import com.example.task9new.presentation.fragments.BaseHabitsListFragment
import com.example.task9new.presentation.fragments.BottomSheetFragment
import com.example.task9new.presentation.fragments.FormFragment
import com.example.task9new.presentation.fragments.HabitsListFragment
import com.example.task9new.presentation.viewModels.FormViewModel
import com.example.task9new.presentation.viewModels.HabitsListViewModel
import com.example.task9new.presentation.viewPager2Entities.HabitsListViewPagerAdapter
import dagger.Component
import java.text.Normalizer
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DomainModule::class, DataModule::class,
    PresentationModule::class,  ContextModule::class])
interface AppComponent {

    fun injectMainActivity(mainActivity: MainActivity)

    //fun injectViewPagerAdapter(viewPagerAdapter: HabitsListViewPagerAdapter)

    fun injectHabitsListFragment(habitsListFragment: HabitsListFragment)

    fun injectBottomSheetFragment(bottomSheetFragment: BottomSheetFragment)

    fun injectFormFragment(formFragment: FormFragment)

    fun injectRepositoryImpl(repositoryImpl: RepositoryImpl)
}