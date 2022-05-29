package com.example.data.testObjects

import com.example.data.service.HabitsService
import com.example.domain.objects.*
import java.util.*

class TestService : HabitsService {

    val testHabitsList = listOf(
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

    override suspend fun getHabits(): List<Habit> {
        return testHabitsList
    }

    override suspend fun putHabit(habit: Habit): HabitUID {
        if (habit.uniqueId.isEmpty()) {
            return HabitUID("testUUID")
        }
        return HabitUID(habit.uniqueId)
    }

    override suspend fun habitDone(habitDone: HabitDone) {
        val habit = testHabitsList.first { it.uniqueId == habitDone.uid }
        habit.doHabit()
    }
}