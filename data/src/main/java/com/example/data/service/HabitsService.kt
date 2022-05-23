package com.example.data.service

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitUID
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface HabitsService {

    @GET("habit")
    suspend fun getHabits(): List<Habit>

    @PUT("habit")
    suspend fun putHabit(@Body habit: Habit): HabitUID

}