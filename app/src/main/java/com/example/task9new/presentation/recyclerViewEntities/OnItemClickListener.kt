package com.example.task9new.presentation.recyclerViewEntities

import com.example.domain.objects.Habit

interface OnItemClickListener {
    fun onItemClicked(habit : Habit) {
    }
}