package com.example.task9new

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.repository.RepositoryImpl
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.example.domain.useCases.GetHabitByIdUseCase
import com.example.domain.useCases.PutHabitUseCase
import com.example.task9new.presentation.viewModels.FormViewModel
import com.example.task9new.testObjects.TestHabitDao
import com.example.task9new.testObjects.TestService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.Rule


class FormViewModelUnitTest {

    lateinit var testRepository: RepositoryImpl

    lateinit var testPutHabitUseCase: PutHabitUseCase

    lateinit var testGetHabitByIdUseCase : GetHabitByIdUseCase

    lateinit var testHabit : Habit

    lateinit var testFormViewModel : FormViewModel

    @get:Rule
    val coroutineRule = MainCoroutineRule()

//    @get:Rule
//    val liveDataRule = InstantTaskExecutorRule()

//    val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setObjects() {
        testRepository = RepositoryImpl(TestService(), TestHabitDao())
        testPutHabitUseCase = PutHabitUseCase(testRepository)
        testGetHabitByIdUseCase = GetHabitByIdUseCase(testRepository)
        testHabit = Habit(
            "newTestHabit",
            "newTestHabit",
            3,
            HabitType.USEFUL,
            10,
            TimeIntervalType.WEEKS
        )

//        Dispatchers.setMain(testDispatcher)
    }

//    @After
//    fun resetDispatcher() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun processAddingFormIsCorrect() = coroutineRule.testDispatcher.runBlockingTest {
        testFormViewModel = FormViewModel("", testGetHabitByIdUseCase, testPutHabitUseCase)
        testHabit.apply {
            coroutineRule.testDispatcher.runBlockingTest { // без этого код корутины почему-то не запускается
                testFormViewModel.processForm(title, description, priority, type, eventsCount, timeIntervalType)
            }
        }

        testHabit.uniqueId = "testUUID"

        val allUsefulHabits = testRepository.habitDao.getCurrentHabits(
            HabitType.USEFUL.resId, "", "").first()

        assertEquals(testHabit, allUsefulHabits.last())
        assertEquals(3, allUsefulHabits.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class) // не работает, потому что значение в LD = null
    @Test
    fun processEditingFormIsCorrect() = coroutineRule.testDispatcher.runBlockingTest {
        val testDao = testRepository.habitDao as TestHabitDao
        testDao.setUIDsForTestHabit()

        testFormViewModel = FormViewModel("3", testGetHabitByIdUseCase, testPutHabitUseCase)
        testHabit.apply {
            coroutineRule.testDispatcher.runBlockingTest {
                testFormViewModel.processForm(title, description, priority, type, eventsCount, timeIntervalType)
            }
        }

        val allUsefulHabits = testRepository.habitDao.getCurrentHabits(
            HabitType.USEFUL.resId, "", "").first()

        testHabit.uniqueId = "3"

        assertEquals(testHabit, allUsefulHabits.last())
        assertEquals(3, allUsefulHabits.size)
    }
}