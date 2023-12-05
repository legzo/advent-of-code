package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import java.lang.Integer.max

fun main() {
    val input = getTextFromFile("v2023/day02.txt")
    measureTimeAndPrint { input.parseAsGames().filter { it.isPossible() }.sumOf { it.id } }
    measureTimeAndPrint { input.parseAsGames().sumOf { it.power() } }
}

data class Game(
    val id: Int,
    val draws: List<Draw>
)

data class Draw(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
)

fun String.parseAsGames(): List<Game> =
    lines().mapNotNull {
        it.parseWithRegex("Game ([0-9]+): (.*)") { (gameId, drawsAsString) ->

            val draws = drawsAsString.split(";").map { drawAsString ->
                Draw(
                    red = drawAsString.findColor("red"),
                    green = drawAsString.findColor("green"),
                    blue = drawAsString.findColor("blue"),
                )
            }

            Game(id = gameId.toInt(), draws = draws)
        }
    }


private fun String.findColor(colorAsString: String): Int =
    split(",")
        .firstOrNull { it.contains(colorAsString) }
        ?.filter { it.isDigit() }
        ?.toInt()
        ?: 0

fun Game.isPossible(): Boolean =
    draws
        .all { it.red <= 12 && it.green <= 13 && it.blue <= 14 }

fun Game.power(): Int {
    val optimalDraw = draws.fold(Draw(0,0,0)) { acc, current ->
        Draw(
            red = max(acc.red, current.red),
            green = max(acc.green, current.green),
            blue = max(acc.blue, current.blue),
        )
    }

    return optimalDraw.red * optimalDraw.green * optimalDraw.blue
}

fun Game.power2(): Int {
    val minRed = draws.maxOf { it.red }
    val minGreen = draws.maxOf { it.green }
    val minBlue = draws.maxOf { it.blue }

    return minRed * minGreen * minBlue
}