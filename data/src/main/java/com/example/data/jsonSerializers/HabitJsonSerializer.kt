package com.example.data.jsonSerializers

import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

class HabitJsonSerializer : JsonSerializer<Habit> {

    companion object{
        private const val EMPTY_STRING = ""
    }

    override fun serialize(src: Habit, typeOfSrc: Type, context: JsonSerializationContext): JsonElement =
        JsonObject().apply {
            addProperty("color", 0)
            addProperty("count", src.count)
            addProperty("date", src.getCurrentDate()) // хорошо бы сделать в привычке
            addProperty("description", src.description)
            addProperty("frequency", src.eventsCount) // тут не очень конечно получается
            addProperty("priority", src.priority - 1)
            addProperty("title", src.title)
            addProperty("type", when(src.type) {
                HabitType.USEFUL -> 0
                HabitType.BAD -> 1
            })

            if (src.uniqueId != EMPTY_STRING) {
                addProperty("uid", src.uniqueId)
            }

            add("done_dates", JsonArray().apply {
                src.doneDates.forEach { date -> add(date) }
            })
        }
}