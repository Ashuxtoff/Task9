package com.example.domain.objects

import com.example.domain.R

enum class HabitType(
// не получилось так: создавал пустой конструктор и задавал дефолтное значение resId - все равно ошибка
// Entities and POJOs must have a usable public constructor. You can have an empty constructor or a constructor whose parameters match the fields (by name and type)
//TODO: попробовать написать TypeConverter для типа привчки и типа временного интервала

//    @ColumnInfo(name = "habit_type_res_id")
        val resId : Int
    ) {

        USEFUL(R.string.usefulHabitKey),
        BAD(R.string.badHabitKey);

    }
