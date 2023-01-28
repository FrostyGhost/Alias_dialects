package com.fg.alias.dialects.utils

import android.content.Context

fun getFormatTime(millis: Long): String {
    val seconds = (millis / 1000 % 60)
    val minutes = (millis / 1000 / 60)
    return String.format("%02d:%02d", minutes, seconds)
}

enum class Vocabulary(val localise: String) {
    ALL("Всі"),
    GAL("Галицький"),
    ZAC("Закарпатський"),
    LVIV("Львівська ґвара")
}

enum class VocabularyAssetFile(val localise: String) {
    GAL("galishina_json.txt"),
    ZAC("zakarpatia_json.txt"),
    LVIV("lviv_json.txt")
}

fun getVocabularyCount(voc: Vocabulary): String {
    return when(voc){
        Vocabulary.ALL -> {
            "1800"
        }
        Vocabulary.GAL -> {
            "260"
        }
        Vocabulary.LVIV ->{
            "1010"
        }
        Vocabulary.ZAC -> {
            "530"
        }
        else -> {""}
    }
}

fun getAllVocabularies(): ArrayList<Vocabulary> {
    val vocabularyList = ArrayList<Vocabulary>()
    vocabularyList.add(Vocabulary.ALL)
    vocabularyList.add(Vocabulary.GAL)
    vocabularyList.add(Vocabulary.LVIV)
    vocabularyList.add(Vocabulary.ZAC)
    return vocabularyList
}

fun getWordsListByVocabulary(vocabularyList: ArrayList<Vocabulary>, context: Context): List<Word> {
    val wordsList = ArrayList<Word>()
    for (item in vocabularyList){
        wordsList.addAll(getWordsListByVocabulary(item, context))
    }
    if (wordsList.isEmpty()){
        return getAllWordArray(context)
    }
    return wordsList
}

private fun getWordsListByVocabulary(voc: Vocabulary, context: Context): List<Word> {
    when(voc){
        Vocabulary.ALL -> {
            return getAllWordArray(context)
        }
        Vocabulary.GAL -> {
            return getWordArrayFromAssets(context, VocabularyAssetFile.GAL.localise)
        }
        Vocabulary.LVIV -> {
            return getWordArrayFromAssets(context, VocabularyAssetFile.LVIV.localise)
        }
        Vocabulary.ZAC ->{
            return getWordArrayFromAssets(context, VocabularyAssetFile.ZAC.localise)
        }
        else -> {
            return getAllWordArray(context)
        }
    }
}

fun getAllWordArray(context: Context): ArrayList<Word> {
    val allArray = ArrayList<Word>()
    allArray.addAll(getWordArrayFromAssets(context, VocabularyAssetFile.GAL.localise))
    allArray.addAll(getWordArrayFromAssets(context, VocabularyAssetFile.LVIV.localise))
    allArray.addAll(getWordArrayFromAssets(context, VocabularyAssetFile.ZAC.localise))
    return allArray
}

fun getAllVocabularyList(context: Context): List<Word> {
    return getAllWordArray(context)
}