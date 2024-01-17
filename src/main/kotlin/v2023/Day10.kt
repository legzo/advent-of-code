package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.v2023.Direction.FromDown
import gg.jte.aoc.v2023.Direction.FromLeft
import gg.jte.aoc.v2023.Direction.FromRight
import gg.jte.aoc.v2023.Direction.FromTop
import gg.jte.aoc.v2023.Pipe.F
import gg.jte.aoc.v2023.Pipe.H
import gg.jte.aoc.v2023.Pipe.J
import gg.jte.aoc.v2023.Pipe.L
import gg.jte.aoc.v2023.Pipe.V
import gg.jte.aoc.v2023.Pipe._7

fun main() {
    val input = getTextFromFile("v2023/day10.txt")
    measureTimeAndPrint {
        PipeMap(input).loopSteps(
            startingAt = PipeContext(
                cell = PipeCell(x = 27, y = 44),
                direction = FromDown
            )
        ) / 2
    }
}

enum class Direction {
    FromTop, FromRight, FromDown, FromLeft
}

data class PipeCell(val x: Int, val y: Int) {
    fun down() = copy(y = y + 1)
    fun right() = copy(x = x + 1)
    fun left() = copy(x = x - 1)
    fun up() = copy(y = y - 1)
}

enum class Pipe(val asString: String, val asPrettyString: String) {
    V("|", "┃"),
    H("-", "━"),
    L("L", "┗"),
    J("J", "┛"),
    _7("7", "┓"),
    F("F", "┏"),
    ;

    companion object {
        fun from(char: Char) = entries.first { it.asString == char.toString() }
    }
}

data class PipeContext(
    val cell: PipeCell,
    val direction: Direction,
    val initial: Boolean = false
) {
    fun down() = copy(cell = cell.down(), direction = FromTop, initial = false)
    fun right() = copy(cell = cell.right(), direction = FromLeft, initial = false)
    fun left() = copy(cell = cell.left(), direction = FromRight, initial = false)
    fun up() = copy(cell = cell.up(), direction = FromDown, initial = false)
}

class PipeMap(asString: String) {

    private val oneLine = asString.replace("\n", "")

    private val width = asString.lines().size

    fun get(x: Int, y: Int): Pipe =
        Pipe.from(oneLine[y * (width) + x])

    fun loopSteps(startingAt: PipeContext): Int =
        startAt(startingAt.copy(initial = true)).takeWhile { it.initial || it.cell != startingAt.cell }.count()

    fun startAt(context: PipeContext): Sequence<PipeContext> = generateSequence(seed = context) {
        when (get(it.cell.x, it.cell.y)) {
            V -> when (it.direction) {
                FromTop -> it.down()
                FromDown -> it.up()
                FromLeft, FromRight -> error("💥")
            }

            H -> when (it.direction) {
                FromLeft -> it.right()
                FromRight -> it.left()
                FromTop, FromDown -> error("💥")
            }

            L -> when (it.direction) {
                FromTop -> it.right()
                FromRight -> it.up()
                FromDown, FromLeft -> error("💥")
            }

            J -> when (it.direction) {
                FromTop -> it.left()
                FromLeft -> it.up()
                FromRight, FromDown -> error("💥")
            }

            _7 -> when (it.direction) {
                FromLeft -> it.down()
                FromDown -> it.left()
                FromRight, FromTop -> error("💥")
            }

            F -> when (it.direction) {
                FromRight -> it.down()
                FromDown -> it.right()
                FromLeft, FromTop -> error("💥")
            }
        }
    }

}