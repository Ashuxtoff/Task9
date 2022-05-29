package com.example.domain

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class HabitUnitTest {

    lateinit var testHabit : Habit

    @Before
    fun setTestHabit() {
        testHabit = Habit(
            "title",
            "description",
            1,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS
        )
    }

    @Test
    fun habitEditingIsCorrect() {
        testHabit.edit(
            "editedTitle",
            "editedDescription",
            2,
            HabitType.BAD,
            11,
            TimeIntervalType.MONTHS
        )

        assertEquals("editedTitle", testHabit.title)
        assertEquals("editedDescription", testHabit.description)
        assertEquals(2, testHabit.priority)
        assertEquals(HabitType.BAD, testHabit.type)
        assertEquals(11, testHabit.eventsCount)
        assertEquals(TimeIntervalType.MONTHS, testHabit.timeIntervalType)
    }

    @Test
    fun habitDoingIsCorrect() {
        testHabit.doHabit()

        assertEquals(1, testHabit.count)
        assertEquals(1, testHabit.doneDates.size)


        testHabit.doHabit()
        assertEquals(2, testHabit.count)
        assertEquals(2, testHabit.doneDates.size)
    }

    @After
    fun resetTestHabit() = setTestHabit()
}