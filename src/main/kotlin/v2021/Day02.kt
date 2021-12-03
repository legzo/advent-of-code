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
    fun moveFrom(startingPosition: Position) : Position

    data class Forward(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(horizontal = startingPosition.horizontal + amount)

    }
    data class Up(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(depth = startingPosition.depth - amount)
    }

    data class Down(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(depth = startingPosition.depth + amount)
    }
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
    steps.fold(initial = initialPosition) { previousPosition, step ->
        step.moveFrom(previousPosition)
    }
