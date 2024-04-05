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
    val startingCell = PipeCell(x = 27, y = 44)

    val startingContext = PipeContext(
        cell = startingCell,
        direction = FromDown
    )

    measureTimeAndPrint {
        PipeMap(input)
            .loop(startingAt = startingContext)
            .nbOfStepsToReachFarthestPoint()
    }

    measureTimeAndPrint {
        findEnclosedTiles(input, startingContext)
            .size
    }
}

data class LoopCell(
    val cell: PipeCell,
    val pipe: Pipe
)

data class Loop(
    val sequence: Sequence<LoopCell>
) {
    fun nbOfStepsToReachFarthestPoint() = sequence.count() / 2
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

fun findEnclosedTiles(inputAsString: String, startingAt: PipeContext): List<PipeCell> {
    val map = PipeMap(inputAsString)
    return map.findEnclosedTiles(map.loop(startingAt).sequence.toList())
}

enum class Pipe(
    val asString: String,
    val asPrettyString: String,
    val next: (PipeContext) -> PipeContext
) {
    V("|", "┃", {
        when (it.direction) {
            FromTop -> it.down()
            FromDown -> it.up()
            FromLeft, FromRight -> error("💥")
        }
    }),
    H("-", "━", {
        when (it.direction) {
            FromLeft -> it.right()
            FromRight -> it.left()
            FromTop, FromDown -> error("💥")
        }
    }),
    L("L", "┗", {
        when (it.direction) {
            FromTop -> it.right()
            FromRight -> it.up()
            FromDown, FromLeft -> error("💥")
        }
    }),
    J("J", "┛", {
        when (it.direction) {
            FromTop -> it.left()
            FromLeft -> it.up()
            FromRight, FromDown -> error("💥")
        }
    }),
    _7("7", "┓", {
        when (it.direction) {
            FromLeft -> it.down()
            FromDown -> it.left()
            FromRight, FromTop -> error("💥")
        }
    }),
    F("F", "┏", {
        when (it.direction) {
            FromRight -> it.down()
            FromDown -> it.right()
            FromLeft, FromTop -> error("💥")
        }
    }),
    ;

    companion object {
        fun from(char: Char) = entries.firstOrNull { it.asString == char.toString() }
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

    fun get(x: Int, y: Int): Pipe? =
        Pipe.from(oneLine[y * width + x])

    fun loop(startingAt: PipeContext): Loop =
        Loop(
            sequence = startAt(startingAt.copy(initial = true))
                .takeWhile { it.initial || it.cell != startingAt.cell }
                .map { LoopCell(cell = it.cell, pipe = get(it.cell.x, it.cell.y)!!) }
        )

    fun startAt(context: PipeContext): Sequence<PipeContext> =
        generateSequence(seed = context) {
            get(it.cell.x, it.cell.y)!!
                .next(it)
        }

    fun findEnclosedTiles(loop: List<LoopCell>): List<PipeCell> {
        val loopCells = loop.map { it.cell }

        return (1..<width).map { y ->
            (1..<width).mapNotNull { x ->
                val currentCell = PipeCell(x, y)

                if (currentCell !in loopCells) {

                    val loopTraversalCount: Int =
                        loop
                            .asSequence()
                            .filter {
                                it.cell.y == y // same row
                                        && it.cell.x >= x // remaining loop cells on the right
                                        && it.pipe != H // horizontal pipes dont matter
                            }
                            .sortedBy { it.cell.x }
                            .map { it.pipe }
                            .windowed(size = 2, step = 1, partialWindows = true) // pairs of consecutive pipes
                            .count {
                                // if pipe on the left is a | then we cross the loop
                                it.first() == V
                                        // else, we can cross the loop if the sequence is FJ or L7
                                        || it == listOf(F, J)
                                        || it == listOf(L, _7)
                            }

                    // if we crossed an odd number of times, we were in the loop!
                    if (loopTraversalCount % 2 != 0) return@mapNotNull currentCell
                }

                return@mapNotNull null
            }
        }.flatten()
    }

}