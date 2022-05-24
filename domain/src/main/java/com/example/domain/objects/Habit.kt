package com.example.domain.objects

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.domain.typeConverters.DoneDatesTypeConverter
import com.example.domain.typeConverters.HabitTypeConverter
import com.example.domain.typeConverters.TimeIntervalTypeConverter
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "habits")
class Habit(
    @ColumnInfo var title: String,
    @ColumnInfo var description: String,
    @ColumnInfo var priority: Int,
    @field:TypeConverters(HabitTypeConverter::class)
    @ColumnInfo(name = "habit_type_res_id")
    var type : HabitType,
    @ColumnInfo(name = "events_count") var eventsCount: Int,
    @field:TypeConverters(TimeIntervalTypeConverter::class)
    @ColumnInfo(name = "time_interval_type_res_id")
    var timeIntervalType : TimeIntervalType
) : Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    var uniqueId : String = ""

    @ColumnInfo var count = 0

    @field:TypeConverters(DoneDatesTypeConverter::class)
    @ColumnInfo(name = "done_dates")
    var doneDates = arrayOf<Long>()


    //val colorString = colorString


    fun edit(newTitle : String, newDescription : String, newPriority: Int,
             newType : HabitType, newEventsCount : Int, newTimeIntervalType : TimeIntervalType) {
        title = newTitle
        description = newDescription
        priority = newPriority
        type = newType
        eventsCount = newEventsCount
        timeIntervalType = newTimeIntervalType
    }

    fun doHabit() {
        count ++
        val newTime : Long = Date().time
        doneDates.plus(newTime)
    }

    fun getCurrentDate() : Long {
        return Date().time
    }


}