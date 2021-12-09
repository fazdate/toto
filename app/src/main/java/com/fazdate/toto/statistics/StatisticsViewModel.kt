package com.fazdate.toto.statistics


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatisticsViewModel : ViewModel() {

    private var _totalPrize = MutableLiveData<Int>()
    val totalPrize : LiveData<Int> = _totalPrize

    private var _totalAmountOfGames = MutableLiveData<Int>()
    val totalAmountOfGames : LiveData<Int> = _totalAmountOfGames

    private var _totalCorrectGuesses = MutableLiveData<Int>()
    val totalCorrectGuesses : LiveData<Int> = _totalCorrectGuesses

    private var _correctGuessRatio = MutableLiveData<Int>()
    val correctGuessRatio : LiveData<Int> = _correctGuessRatio


    fun setTotalPrize(prizeMoney : Int) {
        _totalPrize.value = prizeMoney
    }

    fun setTotalAmountOfGames(amountOfGames : Int) {
        _totalAmountOfGames.value = amountOfGames
    }

    fun setTotalCorrectGuesses(correctGuesses : Int) {
        _totalCorrectGuesses.value = correctGuesses
    }

    fun setCorrectGuessRatio(guessRatio : Int) {
        _correctGuessRatio.value = guessRatio
    }
}