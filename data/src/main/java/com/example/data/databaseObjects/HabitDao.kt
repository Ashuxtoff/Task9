package com.example.data.databaseObjects

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.domain.objects.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits WHERE id = :uuid")
    fun getHabitById(uuid : String) : Flow<Habit>


    @Query(
        "SELECT * FROM habits " +
                "WHERE habit_type_res_id = :typeResId AND title LIKE '%'|| :searchQuery ||'%'" +
                "ORDER BY CASE " +
                "WHEN 'ascending' = :sortingMode THEN priority " +
                "WHEN 'descending' = :sortingMode THEN (priority * -1) " +
                "END ASC"
    )
    fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>>


    @Insert
    fun addHabit(habit: Habit)

    @Query("DELETE FROM habits")
    fun deleteAllHabits()

    @Insert
    fun insertHabits(habits : List<Habit>)

    @Update
    fun editHabit(habit: Habit)
}