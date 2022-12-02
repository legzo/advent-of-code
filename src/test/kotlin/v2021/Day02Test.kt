package gg.jte.aoc.v2021

import gg.jte.aoc.v2021.Step.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day02Test {

    @Test
    fun `should parse input as list of steps`() {
        listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
        ).toListOfSteps() shouldBe listOf(
            Forward(5),
            Down(5),
            Forward(8),
            Up(3),
            Down(8),
            Forward(2),
        )

    }

    @Test
    fun `should move according to steps`() {
        getFinalPosition(
            initialPosition = Position(horizontal = 0, depth = 0),
            steps = listOf(
                Forward(5),
                Down(5),
                Forward(8),
                Up(3),
                Down(8),
                Forward(2),

                )
        ) shouldBe Position(horizontal = 15, depth = 10)
    }

    @Test
    fun `should move according to steps with aim`() {
        getFinalState(
            initialState = State(position = Position(horizontal = 0, depth = 0), aim = 0),
            steps = listOf(
                Forward(5),
                Down(5),
                Forward(8),
                Up(3),
                Down(8),
                Forward(2),

                )
        ).position shouldBe Position(horizontal = 15, depth = 60)
    }

}