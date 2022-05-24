package com.example.data.repository

import android.content.Context
import com.example.data.databaseObjects.AppDatabase
import com.example.data.service.HabitsService
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitDone
import com.example.domain.objects.HabitUID
import com.example.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class  RepositoryImpl(context: Context, val service : HabitsService) : Repository {

    companion object {
        private const val EMPTY_STRING = ""
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
    }

    private val db = AppDatabase.getInstance(context)
    private val habitDao = db.habitDao()




    override suspend fun getCurrentHabits(typeResId : Int, sortingMode : String, searchQuery : String) : Flow<List<Habit>> { // возвращать result от habit
        val networkHabits = service.getHabits() // это все в try
        habitDao.deleteAllHabits()
        habitDao.insertHabits(networkHabits)
        return habitDao.getCurrentHabits(typeResId, sortingMode, searchQuery)
//        if (sortingMode == ASCENDING_SORTING_MODE) {
//            newHabitsFlow.map { list -> list.sortedBy { it.priority } }
//        }
//        if (sortingMode == DESCENDING_SORTING_MODE) {
//            newHabitsFlow.map { list -> list.sortedByDescending { it.priority } }
//        }
//
//        newHabitsFlow = newHabitsFlow.map { list -> list.filter { habit ->
//            habit.type.resId == typeResId && habit.title.contains(searchQuery) } }
//
//        //Log.d("Habits:", newHabitsFlow.toString())
//        return newHabitsFlow // а тут еще catch
    }


    override suspend fun getHabitById(uuid : String) : Flow<Habit> {
        return habitDao.getHabitById(uuid)
    }

    override suspend fun putHabit(habit : Habit) : Flow<Habit> {
        val habitUid : HabitUID = service.putHabit(habit)
        if (habit.uniqueId == EMPTY_STRING) {
            habit.uniqueId = habitUid.uid
            habitDao.addHabit(habit)
        }
        else
        {
            habitDao.editHabit(habit)
        }

        return habitDao.getHabitById(habitUid.uid)
    }

    override suspend fun doHabit(habitDone : HabitDone): Flow<Habit> {
        service.habitDone(habitDone)
        val networkHabits = service.getHabits()
        habitDao.deleteAllHabits()
        habitDao.insertHabits(networkHabits)
        return habitDao.getHabitById(habitDone.uid)
    }
}