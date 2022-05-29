package com.example.domain

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetAllHabitsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllHabitsUseCaseUnitTest {

    lateinit var testHabitsList : List<Habit>

    @Before
    fun setTestHabitsList() {
        testHabitsList = listOf(
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
    }

    @After
    fun resetTestHabitsList() { // убрать?
        setTestHabitsList()
    }

    @Test
    fun usefulHabitsGettingIsCorrect() = runBlockingTest {
        val mockRepository : Repository = mock()
        whenever(mockRepository.getCurrentHabits(HabitType.USEFUL.resId, "", ""))
            .thenReturn(flow { // или тест юзкейсов стоит вынести в Domain слой?
                emit(listOf(testHabitsList[0],testHabitsList[1]))
            })

        val result = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.USEFUL.resId, "", ""
        ).first()

        assertEquals(testHabitsList[0], result[0])
        assertEquals(testHabitsList[1], result[1])
        assertEquals(2, result.size)
    }

    @Test
    fun badHabitsGettingIsCorrect() = runBlockingTest {
        val mockRepository : Repository = mock()
        whenever(mockRepository.getCurrentHabits(HabitType.BAD.resId, "", ""))
            .thenReturn(flow {
                emit(listOf(testHabitsList[2]))
            })

        val result = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.BAD.resId, "", ""
        ).first()

        assertEquals(testHabitsList[2], result[0])
        assertEquals(1, result.size)
    }

    @Test
    fun habitsSortingIsCorrect() = runBlockingTest {
        val mockRepository : Repository = mock()
        whenever(mockRepository.getCurrentHabits(HabitType.USEFUL.resId, "ascending", ""))
            .thenReturn(flow {
                emit(listOf(testHabitsList[1], testHabitsList[0]))
            })

        whenever(mockRepository.getCurrentHabits(HabitType.USEFUL.resId, "descending", ""))
            .thenReturn(flow {
                emit(listOf(testHabitsList[0], testHabitsList[1]))
            })

        val resultAscending = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.USEFUL.resId, "ascending", ""
        ).first()

        val resultDescending = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.USEFUL.resId, "descending", ""
        ).first()

        assertEquals(true, resultAscending[0].priority < resultAscending[1].priority)
        assertEquals(true, resultDescending[0].priority > resultDescending[1].priority)
    }

    @Test
    fun habitsSearchingIsCorrect() = runBlockingTest {
        val mockRepository : Repository = mock()
        whenever(mockRepository.getCurrentHabits(HabitType.USEFUL.resId, "", "e1"))
            .thenReturn(flow {
                emit(listOf(testHabitsList[0]))
            })

        whenever(mockRepository.getCurrentHabits(HabitType.BAD.resId, "", "e1"))
            .thenReturn(flow {
                emit(listOf())
            })


        val resultNotEmptySearch = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.USEFUL.resId, "", "e1"
        ).first()

        val resultEmptySearch = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.BAD.resId, "", "e1"
        ).first()

        assertEquals(testHabitsList[0], resultNotEmptySearch[0])
        assertEquals(1, resultNotEmptySearch.size)
        assertEquals(0, resultEmptySearch.size)
    }

    @Test
    fun sortingAndSearchCombineIsCorrect() = runBlockingTest {
        val mockRepository : Repository = mock()
        whenever(mockRepository.getCurrentHabits(HabitType.USEFUL.resId, "ascending", "e2"))
            .thenReturn(flow {
                emit(listOf(testHabitsList[1]))
            })

        val result = GetAllHabitsUseCase(mockRepository).execute(
            HabitType.USEFUL.resId, "ascending", "e2"
        ).first()

        assertEquals(testHabitsList[1], result[0])
        assertEquals(1, result.size)
    }
}