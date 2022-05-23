package com.example.domain.useCases

import com.example.domain.objects.Habit
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetHabitByIdUseCase(private val repository: Repository) {

    suspend fun execute(uuid : String) : Flow<Habit>? {
        return repository.getHabitById(uuid)
    }
}