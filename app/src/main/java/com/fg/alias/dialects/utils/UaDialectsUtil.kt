package com.fg.alias.dialects.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import com.fg.alias.R
import com.google.gson.Gson

fun generateJson(resources: Resources, context: Context){
    val wordsList = ArrayList<Word>()
    val wordTitleArray = resources.getStringArray(R.array.zac).toMutableList()

    val word1 = ArrayList<String>()
    val word2 = ArrayList<String>()
    var count = 0
    for (item in wordTitleArray){
        if (count == 0){
            word1.add(item)
            count = 1
        }else{
            word2.add(item)
            count = 0
        }
    }

    word2.forEachIndexed { index, element ->
        wordsList.add(Word(word1[index], element, "Закарпатський говір"))
    }

    val gson = Gson().toJson(wordsList)
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", gson)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()

    Log.e("QQ ${word1.size} + ${word2.size}", gson)
}