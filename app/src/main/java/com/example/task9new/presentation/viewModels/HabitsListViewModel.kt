package com.example.task9new.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetAllHabitsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HabitsListViewModel(
    private val repository : Repository,
    private val getAllHabitsUseCase: GetAllHabitsUseCase
) : ViewModel() {

    companion object {
        private const val EMPTY_STRING = ""
    }

    private val mutableIsUsefulCurrent: MutableLiveData<Boolean> = MutableLiveData()
    private val mutableSortingMode: MutableLiveData<String> = MutableLiveData(EMPTY_STRING)
    private val mutableSearchQuery: MutableLiveData<String> = MutableLiveData(EMPTY_STRING)

    private val mutableCurrentHabitsList : MutableLiveData<List<Habit>> = MutableLiveData<List<Habit>>()

    val sortingMode : LiveData<String> = mutableSortingMode
    val searchQuery : LiveData<String> = mutableSearchQuery

    //private val mediatorLiveData = MediatorLiveData<List<Habit>>()

    val  currentHabitsList : LiveData<List<Habit>> = mutableCurrentHabitsList


    fun setCurrentHabitsList(isUsefulHabitsCurrent: Boolean) {
        mutableIsUsefulCurrent.value = isUsefulHabitsCurrent
        loadCurrentListHabits()
        //перевычислять currentHabitList?
        //а мы не можем, это же не мьютабл
    }

    fun setSortingMode(mode : String?) {
        mutableSortingMode.value = mode ?: EMPTY_STRING
        loadCurrentListHabits()
    }

    fun setSearchQuery(query : String?) {
        mutableSearchQuery.value = query ?: EMPTY_STRING
        loadCurrentListHabits()
    }

//    private fun transform(sortingMode : String) : LiveData<List<Habit>> {
//        return repository.getCurrentHabits(HabitType.USEFUL.resId, sortingMode, searchQuery.value ?: "")


    private fun loadCurrentListHabits() {

        viewModelScope.launch(Dispatchers.Main) {

            val habits =
                withContext(Dispatchers.IO) {
                    getAllHabitsUseCase.execute(
                        if (mutableIsUsefulCurrent.value == false) {
                            HabitType.BAD.resId
                        } else HabitType.USEFUL.resId,

                        sortingMode.value ?: EMPTY_STRING,

                        searchQuery.value ?: EMPTY_STRING
                    )
                }

            mutableCurrentHabitsList.value = habits.first()
        }
    }

//    fun getCurrentHabitsList() : LiveData<List<Habit>> {
//        return currentHabitsList
//    }

}