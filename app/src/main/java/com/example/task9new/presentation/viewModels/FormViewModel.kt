package com.example.task9new.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.RepositoryImpl
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetHabitByIdUseCase
import com.example.domain.useCases.PutHabitUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FormViewModel(
    private val uuid : String,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
    private val putHabitByIdUseCase: PutHabitUseCase
    ) : ViewModel(){

    companion object {
        private const val EMPTY_STRING = ""
    }

    private val mutableHabitLD : MutableLiveData<Habit> = MutableLiveData<Habit>()

    val habitLD : LiveData<Habit> = mutableHabitLD

    //private var habit : Habit? = null


    init { // чтобы провадить модель через даггер, можно в отдельном методе дергать полтягивание привычки по юиду, а не предвать его в конструктор
        if (uuid != EMPTY_STRING) {
            viewModelScope.launch (Dispatchers.Main) {
                val habit = withContext(Dispatchers.IO) {
                    getHabitByIdUseCase.execute(uuid)
                }

                mutableHabitLD.value = habit.first()
            }
        }
    }


    fun processForm(title : String,
                    description : String,
                    priority : Int,
                    type : HabitType,
                    eventsCount : Int,
                    timeIntervalType : TimeIntervalType
    ) = viewModelScope.launch (Dispatchers.IO) {

        if (uuid != EMPTY_STRING) {
            val habit = habitLD.value
            habit?.edit(
                title, description, priority, type, eventsCount, timeIntervalType
            )
            if (habit != null) {
                putHabitByIdUseCase.execute(habit)
            }
        }
        else {
            putHabitByIdUseCase.execute(
                Habit(title, description, priority, type, eventsCount, timeIntervalType)
            )
        }
    }
}