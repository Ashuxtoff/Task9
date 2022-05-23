package com.example.domain.useCases

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitParams
import com.example.domain.objects.HabitUID
import com.example.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PutHabitUseCase(private val repository: Repository) {

    suspend fun execute(habit : Habit): Flow<Habit> {
        return repository.putHabit(habit)
    }
}