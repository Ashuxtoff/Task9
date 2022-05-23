package com.example.domain.objects

class HabitParams(
    val title : String,
    val description : String,
    val priority : Int,
    val type: HabitType,
    val eventsCount : Int,
    val timeIntervalType: TimeIntervalType
) {
}