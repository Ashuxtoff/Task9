package com.example.domain.useCases

import com.example.domain.objects.Habit
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetAllHabitsUseCase(private val repository: Repository) {

    suspend fun execute(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>> {
        return repository.getCurrentHabits(typeResId, sortingMode,searchQuery)
    }
}