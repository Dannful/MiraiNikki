package me.dannly.mirainikki.data.converters

import android.net.Uri
import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {

    @TypeConverter
    fun uriListToString(list: List<Uri>): String {
        return JSONArray().apply {
            list.forEach {
                put(it.toString())
            }
        }.toString()
    }

    @TypeConverter
    fun stringToUriList(string: String): List<Uri> {
        return try {
            val jsonArray = JSONArray(string)
            val list = mutableListOf<Uri>()
            for (i in 0 until jsonArray.length())
                list.add(Uri.parse(jsonArray.getString(i)))
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}