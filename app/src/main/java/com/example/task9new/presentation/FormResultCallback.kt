package com.example.task9new.presentation

import com.example.domain.objects.Habit
import com.example.task9new.presentation.fragments.FormFragment

interface FormResultCallback {
    fun processForm(fragment : FormFragment, resultHabit : Habit?, habitId : String?)
}