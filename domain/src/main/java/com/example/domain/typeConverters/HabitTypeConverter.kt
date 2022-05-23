package com.example.domain.typeConverters

import androidx.room.TypeConverter
import com.example.domain.R
import com.example.domain.objects.HabitType

class HabitTypeConverter {

    @TypeConverter
    fun fromHabitType(type : HabitType) : Int {
        return type.resId
    }

    @TypeConverter
    fun toHabitType(resId : Int) : HabitType {
        if (resId == R.string.usefulHabitKey) {
            return HabitType.USEFUL
        }
        return HabitType.BAD
    }
}