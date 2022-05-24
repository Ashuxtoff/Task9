package com.example.domain.repository

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitDone
import com.example.domain.objects.HabitUID
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>>

    suspend fun getHabitById(uuid : String) : Flow<Habit>

    suspend fun putHabit(habit : Habit) : Flow<Habit>

    suspend fun doHabit(habitDone : HabitDone) : Flow<Habit>
}