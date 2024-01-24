package com.fg.alias.dialects.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fg.alias.dialects.utils.Team
import com.fg.alias.dialects.utils.Word
import java.util.concurrent.TimeUnit

class GameStateViewModel : ViewModel() {

    private var wordsList = ArrayList<Word>()
    private var usedWordsList = ArrayList<Word>()
    private var tempUsedWordsList = ArrayList<Word>()
    private var teamsList = ArrayList<Team>()
    private var currentTeamPosition: Int = 0
    private var roundTime = TimeUnit.MINUTES.toMillis(1)
    private var roundMaxScore = 100
    var currentTeamName = MutableLiveData<String>()
    var isAdditionalTimeForLastWork = true

    fun clear(){
        currentTeamName.postValue("")
        roundMaxScore = 100
        roundTime = TimeUnit.MINUTES.toMillis(1)
        currentTeamPosition = 0
        teamsList.clear()
        tempUsedWordsList.clear()
        usedWordsList.clear()
        wordsList.clear()
    }

    fun setMaxScore(score: Int){
        roundMaxScore = score
    }

    fun setRoundTime(time: Long){
        roundTime = time
    }

    fun getRoundTime(): Long {
        return roundTime
    }

    fun getTeamsList(): ArrayList<Team> {
        return teamsList
    }

    fun setTeams(teamNameStringList: ArrayList<String>){
        for (team in teamNameStringList){
            teamsList.add(Team(team, 0, 0))
        }
        currentTeamName.postValue(teamsList[currentTeamPosition].name)
    }

    fun setWordsList(list : ArrayList<Word>){
        wordsList.clear()
        wordsList = list
    }

    fun getWordsList(): ArrayList<Word> {
        if (wordsList.size <= 20){
            wordsList.clear()
            wordsList.addAll(usedWordsList)
            usedWordsList.clear()
        }
        wordsList.shuffle()
        addTitleCard()

        return wordsList
    }

    fun getWordListSize(): Int {
        return wordsList.size
    }

    private fun addTitleCard(){
        wordsList.add(0,Word("Щоб розпочати гру \n Свайпнійть в будь-яку сторону",
            "якщо відповідь вірна,\nсвайпніть вправо → \nабо вгору ↑ \nабо ← вліво чи вниз ↓, \nякщо відповіль хибна", START_CARD))
    }

    fun saveTeamResult(score: Int): Team? {
        usedWordsList.addAll(tempUsedWordsList)
        tempUsedWordsList.clear()

        val team = teamsList[currentTeamPosition]
        team.score+=score
        team.round+=1

        currentTeamPosition += 1
        if (currentTeamPosition >=teamsList.size){
            currentTeamPosition = 0
            currentTeamName.postValue(teamsList[currentTeamPosition].name)
            return checkWinner()
        }
        currentTeamName.postValue(teamsList[currentTeamPosition].name)
        return null
    }

    private fun checkWinner(): Team? {
        var maxScoreTeam : Team? = null
        for (team in teamsList){
            if (team.score >= roundMaxScore){
                maxScoreTeam = team
            }
        }
        return maxScoreTeam
    }

    fun addUsedWord(word: Word, isLastCardCorrect: Boolean){
        if (word.region == START_CARD){
            return
        }else{
            word.isCorrect = isLastCardCorrect
            tempUsedWordsList.add(word)
        }
    }

    fun getTempUsedWordList(): ArrayList<Word> {
        return tempUsedWordsList
    }

    companion object {
        const val START_CARD = ""
    }
}