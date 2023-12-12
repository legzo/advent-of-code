package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import kotlin.math.pow

fun main() {
    val input = getTextFromFile("v2023/day04.txt")
    measureTimeAndPrint { input.parseAsCards().calculateScores().sum() }
}

data class Card(val id: Int, val winningNumbers: List<Int>, val drawnNumbers: List<Int>)

fun String.parseAsCards(): List<Card> =
    lines().map {
        val (cardIdAsString, winningNumbers, drawnNumbers) = it.split(Regex("[:|]"))
        Card(
            id = cardIdAsString.removePrefix("Card ").trim().toInt(),
            winningNumbers = toInts(winningNumbers),
            drawnNumbers = toInts(drawnNumbers)
        )
    }

fun List<Card>.findWinningNumbers(): Map<Card, List<Int>> =
    associate {
        it to it.drawnNumbers.filter { drawnNumber -> drawnNumber in it.winningNumbers }
    }

fun List<Card>.calculateScores(): List<Int> =
    findWinningNumbers()
        .map { (_, winningNumbers) -> 2f.pow(winningNumbers.size - 1).toInt() }


private fun toInts(winningNumbers: String) =
    winningNumbers
        .trim()
        .split(Regex("\\s+"))
        .map(String::toInt)

