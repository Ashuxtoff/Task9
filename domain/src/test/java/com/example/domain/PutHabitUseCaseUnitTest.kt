package com.example.domain

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.example.domain.repository.Repository
import com.example.domain.useCases.DoHabitUseCase
import com.example.domain.useCases.PutHabitUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatcher
import org.mockito.Mockito

class PutHabitUseCaseUnitTest {

    private fun <T> argThat(matcher : ArgumentMatcher<T>): T { // чтобы не падал NullPointer
        Mockito.argThat<T>(matcher)
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

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
        testHabit.uniqueId = "testUUID"
    }

    @Test
    fun executionTest() = runBlockingTest { // не очень понятно, как правильно тестировать
        val mockRepository : Repository = mock() // методы юзкейсов, потому что они возвращают
        whenever(mockRepository.putHabit( // результаты из репозитория, а репозитори надо мокать,
            testHabit // потому что иначе нарушится Dependency Rule
        )).thenReturn(flow { // или тест юзкейсов стоит вынести в Domain слой?
            emit(testHabit)
        })

        val result = PutHabitUseCase(mockRepository).execute(testHabit)

        assertEquals(testHabit, result.first())
    }
}