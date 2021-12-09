package com.fazdate.toto.statistics

import java.io.Serializable
data class GameObject(val numberOfGames: Int, val numberOfCorrectGuesses: Int, val prizeMoney: Int) : Serializable
