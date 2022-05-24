package com.example.domain.useCases

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitDone
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class DoHabitUseCase(private val repository: Repository) {

    suspend fun execute(habit : Habit) : Flow<Habit> {
        habit.doHabit()
        val habitDone = HabitDone(habit.doneDates.last(), habit.uniqueId)
        return repository.doHabit(habitDone)
    }
}