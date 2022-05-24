package com.example.data.jsonSerializers

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitDone
import com.example.domain.objects.HabitType
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.*

class HabitDoneSerializer : JsonSerializer<HabitDone> {

    override fun serialize(src: HabitDone, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement =
        JsonObject().apply {
            addProperty("date", src.date)
            addProperty("habit_uid", src.uid)
        }

}