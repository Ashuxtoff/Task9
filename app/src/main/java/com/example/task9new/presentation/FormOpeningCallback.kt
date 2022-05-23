package com.example.task9new.presentation

import com.example.domain.objects.Habit

interface FormOpeningCallback {
    fun openForm(habitForEditing : Habit?)
}