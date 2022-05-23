package com.example.task9new.presentation.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.RepositoryImpl
import com.example.domain.objects.Habit
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetHabitByIdUseCase
import com.example.domain.useCases.PutHabitUseCase
import com.example.task9new.presentation.fragments.*
import com.example.task9new.presentation.viewModels.FormViewModel
import com.example.task9new.presentation.viewModels.HabitsListViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule {

//    @Provides
//    @Singleton
//    fun provideHabitsListViewModel(fragment: HabitsListFragment, context : Context) : HabitsListViewModel {
//        return ViewModelProvider(fragment, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return HabitsListViewModel(RepositoryImpl(context)) as T
//            }
//        })[HabitsListViewModel::class.java]
//    }

    @Provides
    fun provideFormViewModule(repository : Repository, uuid : String,
                              getHabitByIdUseCase: GetHabitByIdUseCase,
                              putHabitUseCase: PutHabitUseCase) : FormViewModel {
        // переделать юид на объект нэбит юид?
        return FormViewModel(repository, uuid, getHabitByIdUseCase, putHabitUseCase)
    }

    @Provides
    fun provideHabitsListFragment() : HabitsListFragment {
        return HabitsListFragment()
    }

    @Provides
    @Singleton
    fun provideFormFragment() : FormFragment {
        return FormFragment()
    }

    @Provides
    fun provideBottomSheetFragment() : BottomSheetFragment {
        return BottomSheetFragment()
    }

    @Provides
    @Singleton
    fun provideBaseHabitsListFragment() : BaseHabitsListFragment {
        return BaseHabitsListFragment()
    }

    @Provides
    fun provideAppInformationFragment() : AppInformationFragment {
        return AppInformationFragment()
    }
}