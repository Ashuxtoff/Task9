package com.example.data.jsonSerializers

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class HabitJsonDeserializer : JsonDeserializer<Habit> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Habit {
        val habit = Habit(
            json.asJsonObject.get("title").asString,
            json.asJsonObject.get("description").asString,
            json.asJsonObject.get("priority").asInt + 1,

            when (json.asJsonObject.get("type").asInt) {
                0 -> HabitType.USEFUL
                else -> HabitType.BAD
            },

            json.asJsonObject.get("frequency").asInt,
            TimeIntervalType.WEEKS
        )

        habit.uniqueId = json.asJsonObject.get("uid").asString

        habit.count = json.asJsonObject.get("count").asInt

        json.asJsonObject.get("done_dates").asJsonArray.forEach {habit.doneDates.plus(it.asLong)}

        return habit
    }
}