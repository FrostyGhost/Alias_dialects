package com.fg.alias.dialects.utils

import android.content.Context

fun getFormatTime(millis: Long): String {
    val seconds = (millis / 1000 % 60)
    val minutes = (millis / 1000 / 60)
    return String.format("%02d:%02d", minutes, seconds)
}

data class VocabularyItem(
    val voc: Vocabulary,
    var isSelected: Boolean = false
)

enum class Vocabulary(val localise: String) {
    ALL("Всі"),
    GAL("Галицький"),
    ZAC("Закарпатський"),
    POD("Подільський говір"),
    ODESA("Одеські говірки"),
    LVIV("Львівська ґвара")
}

enum class VocabularyAssetFile(val localise: String) {
    GAL("galishina_json.txt"),
    ZAC("zakarpatia_json.txt"),
    LVIV("lviv_json.txt"),
    POD("podilskiy_json.txt"),
    ODESA("odeski_json.txt")
}

fun getVocabularyCount(voc: Vocabulary): String {
    return when(voc){
        Vocabulary.ALL -> {
            "2097"
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
        Vocabulary.POD ->{
            "118"
        }
        Vocabulary.ODESA ->{
            "179"
        }
        else -> {""}
    }
}

fun getAllVocabularies(): ArrayList<VocabularyItem> {
    val vocabularyList = ArrayList<VocabularyItem>()
    vocabularyList.add(VocabularyItem(Vocabulary.ALL))
    vocabularyList.add(VocabularyItem(Vocabulary.GAL))
    vocabularyList.add(VocabularyItem(Vocabulary.LVIV))
    vocabularyList.add(VocabularyItem(Vocabulary.ZAC))
    vocabularyList.add(VocabularyItem(Vocabulary.POD))
    vocabularyList.add(VocabularyItem(Vocabulary.ODESA))
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
        Vocabulary.POD ->{
            return getWordArrayFromAssets(context, VocabularyAssetFile.POD.localise)
        }
        Vocabulary.ODESA ->{
            return getWordArrayFromAssets(context, VocabularyAssetFile.ODESA.localise)
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
    allArray.addAll(getWordArrayFromAssets(context, VocabularyAssetFile.POD.localise))
    allArray.addAll(getWordArrayFromAssets(context, VocabularyAssetFile.ODESA.localise))
    return allArray
}

fun getAllVocabularyList(context: Context): List<Word> {
    return getAllWordArray(context)
}