package com.example.domain.di

import com.example.domain.repository.Repository
import com.example.domain.useCases.DoHabitUseCase
import com.example.domain.useCases.GetAllHabitsUseCase
import com.example.domain.useCases.GetHabitByIdUseCase
import com.example.domain.useCases.PutHabitUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideDoHabitUseCase(repository: Repository) : DoHabitUseCase {
        return DoHabitUseCase(repository)
    }

    @Provides
    fun provideGetAllHabitsUseCase(repository: Repository) : GetAllHabitsUseCase {
        return GetAllHabitsUseCase(repository)
    }

    @Provides
    fun provideGetHabitByIdUseCase(repository: Repository) : GetHabitByIdUseCase {
        return GetHabitByIdUseCase(repository)
    }

    @Provides
    fun providePutHabitUseCase(repository: Repository) : PutHabitUseCase {
        return PutHabitUseCase(repository)
    }
}