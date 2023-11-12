package dev.passerby.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson

class JsonConverters {
    @TypeConverter
    fun stringListToJson(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun doubleListToJson(value: List<Double>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToDoubleList(value: String) = Gson().fromJson(value, Array<Double>::class.java).toList()
}