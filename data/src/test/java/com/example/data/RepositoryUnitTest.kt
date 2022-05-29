package com.example.data

import com.example.data.repository.RepositoryImpl
import com.example.data.testObjects.TestHabitDao
import com.example.data.testObjects.TestService
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitDone
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*


class RepositoryUnitTest {

    lateinit var testRepository: RepositoryImpl

    val testHabitsList = listOf(
        Habit(
            "title1",
            "description",
            2,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS
        ),

        Habit(
            "title2",
            "description",
            1,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS
        ),

        Habit(
            "_title_",
            "description",
            1,
            HabitType.BAD,
            10,
            TimeIntervalType.WEEKS
        )
    )

    @Before
    fun setRepository() {
        testRepository = RepositoryImpl(TestService(), TestHabitDao())
    }

    @Test
    fun getCurrentHabitsIsCorrect() = runBlockingTest {

        val usefulHabits = testRepository.getCurrentHabits(HabitType.USEFUL.resId, "", "").first()
        val badHabits = testRepository.getCurrentHabits(HabitType.BAD.resId, "", "").first()
        val usefulSortedAscendingHabits =
            testRepository.getCurrentHabits(HabitType.USEFUL.resId, "ascending", "").first()
        val usefulSortedDescendingHabits =
            testRepository.getCurrentHabits(HabitType.USEFUL.resId, "descending", "").first()
        val usefulSearchedHabits = testRepository.getCurrentHabits(HabitType.USEFUL.resId, "", "e1").first()
        val badSearchedHabits = testRepository.getCurrentHabits(HabitType.BAD.resId, "", "e1").first()
        val usefulSortedAndSearchedHabits =
            testRepository.getCurrentHabits(HabitType.USEFUL.resId, "ascending", "e1").first()

        val usefulHabitsCorrect = testHabitsList.slice(0..1)
        val badHabitsCorrect = mutableListOf(testHabitsList[2])
        val usefulSortedAscendingHabitsCorrect = usefulHabitsCorrect.sortedBy { it -> it.priority }
        val usefulSortedDescendingHabitsCorrect = usefulHabitsCorrect.sortedBy { it -> it.priority * -1 }
        val usefulSearchedHabitsCorrect = mutableListOf(testHabitsList[0])
        val badSearchedHabitsCorrect = mutableListOf<Habit>()
        val usefulSortedAndSearchedHabitsCorrect = mutableListOf(testHabitsList[0])

        assertEquals(usefulHabits, usefulHabitsCorrect)
        assertEquals(badHabits, badHabitsCorrect)
        assertEquals(usefulSortedAscendingHabits, usefulSortedAscendingHabitsCorrect)
        assertEquals(usefulSortedDescendingHabits, usefulSortedDescendingHabitsCorrect)
        assertEquals(usefulSearchedHabits, usefulSearchedHabitsCorrect)
        assertEquals(badSearchedHabits, badSearchedHabitsCorrect)
        assertEquals(usefulSortedAndSearchedHabits, usefulSortedAndSearchedHabitsCorrect)
    }

    @Test
    fun getHabitByIdIsCorrect() = runBlockingTest {
        val testDao = testRepository.habitDao as TestHabitDao
        testDao.setUIDsForTestHabit()

        val gettingFirstHabitResult = testRepository.getHabitById("1").first()
        val gettingSecondHabitResult = testRepository.getHabitById("2").first()
        val gettingThirdHabitResult = testRepository.getHabitById("3").first()

        assertEquals(testHabitsList[0], gettingFirstHabitResult)
        assertEquals(testHabitsList[1], gettingSecondHabitResult)
        assertEquals(testHabitsList[2], gettingThirdHabitResult)
    }

    @Test
    fun putNotExistingHabitIsCorrect() = runBlockingTest {
        val testDao = testRepository.habitDao as TestHabitDao
        testDao.setUIDsForTestHabit()

        val newHabit = Habit(
            "newHabit",
            "description",
            1,
            HabitType.BAD,
            10,
            TimeIntervalType.WEEKS)

        val putResult = testRepository.putHabit(newHabit).first()

        assertEquals(4, testDao.testHabitsList.size)
        assertEquals("testUUID", putResult.uniqueId)
    }

    @Test
    fun putExistingHabitIsCorrect() = runBlockingTest {
        val testDao = testRepository.habitDao as TestHabitDao
        testDao.setUIDsForTestHabit()

        val newHabit = Habit(
            "newHabit",
            "description",
            1,
            HabitType.BAD,
            10,
            TimeIntervalType.WEEKS)

        newHabit.uniqueId = "1"

        val putResult = testRepository.putHabit(newHabit).first()

        assertEquals(3, testDao.testHabitsList.size)
        assertEquals(newHabit, putResult)
        assertEquals(newHabit, testDao.testHabitsList[0])
    }

    @Test
    fun habitDoingIsCorrect() = runBlockingTest {
        val testDao = testRepository.habitDao as TestHabitDao
        testDao.setUIDsForTestHabit()

        val testService = testRepository.service as TestService
        testService.setUIDsForTestHabit()

        val testHabitDone = HabitDone(
            Date().time, "1"
        )

        val firstDoingResult = testRepository.doHabit(testHabitDone).first()

        assertEquals(1, firstDoingResult.doneDates.size)
        assertEquals(1, firstDoingResult.count)

        val secondDoingResult = testRepository.doHabit(testHabitDone).first()

        assertEquals(2, secondDoingResult.doneDates.size)
        assertEquals(2, firstDoingResult.count)
        assertEquals(true, secondDoingResult.doneDates.last() > secondDoingResult.doneDates.first())
    }
}