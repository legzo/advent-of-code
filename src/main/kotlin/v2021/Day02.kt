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

    val finalState = getFinalState(
        initialState = State(position = Position(horizontal = 0, depth = 0), aim = 0),
        steps = input.toListOfSteps()
    )

    with (finalState.position) { println(horizontal * depth) }
}

sealed interface Step {
    val amount: Int
    fun moveFrom(startingPosition: Position): Position
    fun nextFrom(startingState: State): State

    data class Forward(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(horizontal = startingPosition.horizontal + amount)

        override fun nextFrom(startingState: State): State =
            startingState.copy(
                position = startingState.position.copy(
                    horizontal = startingState.position.horizontal + amount,
                    depth = startingState.position.depth + startingState.aim * amount
                )
            )
    }

    data class Up(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(depth = startingPosition.depth - amount)

        override fun nextFrom(startingState: State): State =
            startingState.copy(aim = startingState.aim - amount)
    }

    data class Down(override val amount: Int) : Step {
        override fun moveFrom(startingPosition: Position) =
            startingPosition.copy(depth = startingPosition.depth + amount)

        override fun nextFrom(startingState: State): State =
            startingState.copy(aim = startingState.aim + amount)
    }
}

data class State(
    val position: Position,
    val aim: Int
)

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

fun getFinalState(
    initialState: State,
    steps: List<Step>
): State =
    steps.fold(initial = initialState) { previousState, step ->
        step.nextFrom(previousState)
    }