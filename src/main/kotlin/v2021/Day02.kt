package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.v2021.Step.Down
import gg.jte.aoc.v2021.Step.Forward
import gg.jte.aoc.v2021.Step.Up

fun main() {
    val input = getLinesFromFile("v2021/day_02.txt")
    val finalPosition = getFinalPosition(
        initialPosition = Position(horizontal = 0, depth = 0),
        steps = input.toListOfSteps()
    )

    println(finalPosition.horizontal * finalPosition.depth)
}

sealed interface Step {
    val amount: Int

    data class Forward(override val amount: Int) : Step
    data class Up(override val amount: Int) : Step
    data class Down(override val amount: Int) : Step
}

data class Position(
    val horizontal: Int,
    val depth: Int
)

fun List<String>.toListOfSteps(): List<Step> =
    mapNotNull {
        val (instructionAsString, amountAsString) = it.split(" ")
        val amount = amountAsString.toInt()
        when (instructionAsString) {
            "forward" -> Forward(amount)
            "up" -> Up(amount)
            "down" -> Down(amount)
            else -> null
        }

    }

fun getFinalPosition(
    initialPosition: Position,
    steps: List<Step>
): Position =
    steps.fold(initial = initialPosition) { acc, step ->
        when (step) {
            is Down -> Position(acc.horizontal, acc.depth + step.amount)
            is Forward -> Position(acc.horizontal + step.amount, acc.depth)
            is Up -> Position(acc.horizontal, acc.depth - step.amount)
        }
    }
