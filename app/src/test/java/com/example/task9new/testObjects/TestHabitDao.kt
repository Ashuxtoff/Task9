package com.example.task9new.testObjects

import com.example.data.databaseObjects.HabitDao
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestHabitDao : HabitDao {

    companion object {
        private const val EMPTY_STRING = ""
        private const val ASCENDING_SORTING_MODE = "ascending"
        private const val DESCENDING_SORTING_MODE = "descending"
    }

    var testHabitsList = mutableListOf(
        Habit(
            "title1",
            "description",
            2,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS),

        Habit(
            "title2",
            "description",
            1,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS),

        Habit(
            "_title_",
            "description",
            1,
            HabitType.BAD,
            10,
            TimeIntervalType.WEEKS)
    )

    fun setUIDsForTestHabit() {
        testHabitsList[0].uniqueId = "1"
        testHabitsList[1].uniqueId = "2"
        testHabitsList[2].uniqueId = "3"
    }

    override fun getHabitById(uuid: String): Flow<Habit> {
        return flow {
            emit(testHabitsList.first { it.uniqueId == uuid })
        }
    }

    override fun getCurrentHabits(typeResId: Int, sortingMode: String, searchQuery: String): Flow<List<Habit>> {
        var habits = testHabitsList.toList()

        if (sortingMode == ASCENDING_SORTING_MODE) {
            habits = habits.sortedBy { it.priority }
        }
        if (sortingMode == DESCENDING_SORTING_MODE) {
            habits = habits.sortedByDescending { it.priority }
        }

        habits = habits.filter { habit ->
            habit.type.resId == typeResId && habit.title.contains(searchQuery)
        }

        return flow {
            emit(habits)
        }


    }

    override fun addHabit(habit: Habit) {
        testHabitsList.add(habit)
    }

    override fun deleteAllHabits() {
        testHabitsList.clear()
    }

    override fun insertHabits(habits: List<Habit>) {
        habits.forEach { testHabitsList.add(it) }
    }

    override fun editHabit(habit: Habit) {
        val existingHabit = testHabitsList.first { it.uniqueId == habit.uniqueId }

        existingHabit.edit(
            habit.title,
            habit.description,
            habit.priority,
            habit.type,
            habit.eventsCount,
            habit.timeIntervalType
        )
    }
}