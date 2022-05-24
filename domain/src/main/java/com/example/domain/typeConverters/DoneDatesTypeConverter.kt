package com.example.domain.typeConverters

import androidx.room.TypeConverter

class DoneDatesTypeConverter {

    @TypeConverter
    fun fromDoneDates(doneDates : Array<Long>) : String {
        return doneDates.joinToString(",")
    }

    @TypeConverter
    fun toDoneDates(data : String) : Array<Long> {
        if (data.isEmpty())
            return arrayOf()
        return data.split(",").map { it.toLong() }.toTypedArray()
    }
}