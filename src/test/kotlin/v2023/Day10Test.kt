package gg.jte.aoc.v2023

import gg.jte.aoc.v2023.Direction.FromDown
import gg.jte.aoc.v2023.Direction.FromLeft
import gg.jte.aoc.v2023.Direction.FromRight
import gg.jte.aoc.v2023.Direction.FromTop
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val input = """
    .....
    .F-7.
    .|.|.
    .L-J.
    .....
    """.trimIndent()

    private val input2 = """
    ..F7.
    .FJ|.
    FJ.L7
    |F--J
    LJ...
    """.trimIndent()

    @Test
    fun `should get pipe at coords`() {
        PipeMap(input).get(1, 1) shouldBe Pipe.F
        PipeMap(input).get(2, 1) shouldBe Pipe.H
        PipeMap(input).get(3, 1) shouldBe Pipe._7

        PipeMap(input).get(1, 2) shouldBe Pipe.V
    }

    @Test
    fun `should get next cell`() {
        val context = PipeContext(PipeCell(1, 1), FromDown)
        val pipeMoves = PipeMap(input).startAt(context).take(10).toList()

        pipeMoves[0] shouldBe PipeContext(PipeCell(1, 1), FromDown)
        pipeMoves[1] shouldBe PipeContext(PipeCell(2, 1), FromLeft)
        pipeMoves[2] shouldBe PipeContext(PipeCell(3, 1), FromLeft)
        pipeMoves[3] shouldBe PipeContext(PipeCell(3, 2), FromTop)
        pipeMoves[4] shouldBe PipeContext(PipeCell(3, 3), FromTop)
        pipeMoves[5] shouldBe PipeContext(PipeCell(2, 3), FromRight)
        pipeMoves[6] shouldBe PipeContext(PipeCell(1, 3), FromRight)
        pipeMoves[7] shouldBe PipeContext(PipeCell(1, 2), FromDown)
        pipeMoves[8] shouldBe PipeContext(PipeCell(1, 1), FromDown)
    }

    @Test
    fun `should get next cell more complex input`() {
        val context = PipeContext(PipeCell(0, 2), FromDown)
        val pipeMoves = PipeMap(input2).startAt(context).take(10).toList()

        pipeMoves[8] shouldBe PipeContext(PipeCell(4, 2), FromLeft)
    }

    @Test
    fun `should count steps of loop`() {
        val context = PipeContext(PipeCell(1, 1), FromDown)
        PipeMap(input).loopSteps(context) shouldBe 8

        val context2 = PipeContext(PipeCell(0, 2), FromDown)
        PipeMap(input2).loopSteps(context2) shouldBe 16
    }
}