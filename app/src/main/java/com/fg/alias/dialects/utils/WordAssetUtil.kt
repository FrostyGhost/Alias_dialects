package com.fg.alias.dialects.utils

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject

fun getWordArrayFromJson(json: String): List<Word> {
    val lvlJson = JSONObject(json)
    return Gson().fromJson(lvlJson.getString("wordsList").toString(), Array<Word>::class.java).toList()
}

fun getWordArrayFromAssets(context: Context, fileName: String): ArrayList<Word> {
    return getWordArrayFromJson(context.assets
        .open(fileName)
        .bufferedReader().use {
            it.readText()
        }) as ArrayList<Word>
}