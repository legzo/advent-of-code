package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import gg.jte.aoc.v2022.Choice.values

fun main() {
    val input = getTextFromFile("v2022/day02.txt")
    measureTimeAndPrint { parseAsRounds(input, Round::asChoices).sumOf { it.score } }
    measureTimeAndPrint { parseAsRounds(input, Round::asPrediction).sumOf { it.score } }
}

fun parseAsRounds(input: String, toRoundFunction: (String, String) -> Round): List<Round> =
    input
        .split("\n")
        .mapNotNull {
            it.parseWithRegex("([ABC]) ([XYZ])") { (opponent, own) ->
                toRoundFunction(opponent, own)
            }
        }

private fun toChoice(stringValue: String): Choice =
    values().first { stringValue in it.stringRepresentations }

enum class Choice(
    val score: Int,
    val stringRepresentations: List<String>,
    val defeater: String
) {
    Rock(score = 1, stringRepresentations = listOf("A", "X"), defeater = "B"),
    Paper(score = 2, stringRepresentations = listOf("B", "Y"), defeater = "C"),
    Scissors(score = 3, stringRepresentations = listOf("C", "Z"), defeater = "A");
}

data class Round(
    val opponent: Choice,
    val own: Choice
) {
    private val itsAWin = own == toChoice(opponent.defeater)
    private val itsADraw = opponent == own

    val score: Int = own.score + when {
        itsAWin -> 6
        itsADraw -> 3
        else -> 0
    }

    companion object {
        fun asChoices(opponent: String, own: String) =
            Round(toChoice(opponent), toChoice(own))

        fun asPrediction(opponent: String, outcome: String): Round {
            val opponentChoice = toChoice(opponent)
            val ownChoice = when (outcome) {
                "X" -> Choice.values().first { it.defeater == opponent }
                "Y" -> opponentChoice
                "Z" -> toChoice(opponentChoice.defeater)
                else -> throw IllegalStateException("Should not happen")
            }
            return Round(opponentChoice, ownChoice)
        }
    }
}