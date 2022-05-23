package com.example.domain.typeConverters

import androidx.room.TypeConverter
import com.example.domain.R
import com.example.domain.objects.TimeIntervalType

class TimeIntervalTypeConverter {

    @TypeConverter
    fun fromTimeIntervalType(type : TimeIntervalType) : Int {
        return type.resId
    }

    @TypeConverter
    fun toTimeIntervalType(resId : Int) : TimeIntervalType {
        return when (resId) {
            R.string.hourTimeIntervalKey -> TimeIntervalType.HOURS
            R.string.dayTimeIntervalKey -> TimeIntervalType.DAYS
            R.string.weekTimeIntervalKey -> TimeIntervalType.WEEKS
            else -> TimeIntervalType.MONTHS
        }
    }
}