package com.example.myreceipeapp.data.Local.convertor

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class Convertors {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Json.encodeToString(ListSerializer(String.serializer()), list)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return Json.decodeFromString(ListSerializer(String.serializer()), data)
    }
}