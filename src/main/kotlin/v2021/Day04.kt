package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile
import java.lang.IllegalStateException
import kotlin.math.sqrt

fun main() {
    val lines = getLinesFromFile("v2021/day_04.txt")

    val drawNumbers: List<Int> = lines.first().split(",").map { it.toInt() }

    val boards = lines
        .drop(2) // deux premières lignes = drawNumbers + ligne vide
        .windowed(size = 5, step = 6) //equivalent à .chunked(6) { it.dropLast(1) }
        .map { Board.from(it) }

    println(findFirstWinningBoard(boards, drawNumbers).score)
}

fun findFirstWinningBoard(
    boards: List<Board>,
    drawNumbers: List<Int>
): BoardAndLastDrawNumber {
    drawNumbers
        .fold(boards) { oldBoards, drawNumber ->
            val newBoards = oldBoards.mark(drawNumber)

            newBoards
                .firstOrNull { it.wins() }
                ?.let { return BoardAndLastDrawNumber(it, drawNumber) }

            newBoards
        }

    throw IllegalStateException("No winnning board found")
}

data class BoardAndLastDrawNumber(
    val board: Board,
    val lastDrawNumber: Int
) {
    val score = board.sumOfUnmarkedNumbers() * lastDrawNumber
}

data class Cell(
    val x: Int,
    val y: Int,
    val number: Int,
    val isMarked: Boolean
)

data class Board(
    val cells: List<Cell>,
    val size: Int = 5
) {

    fun at(
        x: Int,
        y: Int
    ): Cell? = cells.firstOrNull { it.x == x && it.y == y }

    fun mark(number: Int): Board =
        copy(
            cells = cells.fold(listOf()) { acc, cell ->
                acc + if (cell.number == number) cell.copy(isMarked = true) else cell
            }
        )

    fun wins(): Boolean {
        return (0 until sqrt(cells.size.toDouble()).toInt())
            .any { index ->
                row(index).all { it.isMarked } || column(index).all { it.isMarked }
            }
    }

    fun sumOfUnmarkedNumbers() =
        cells
            .filter { !it.isMarked }
            .sumOf { it.number }

    private fun row(index: Int) =
        cells.filter { it.y == index }

    private fun column(index: Int) =
        cells.filter { it.x == index }

    companion object {
        fun from(rowsAsString: List<String>): Board =
            Board(cells = rowsAsString
                .flatMapIndexed { rowIndex, row ->
                    row
                        .trim()
                        .split(Regex("\\s+"))
                        .mapIndexed { columnIndex, number ->
                            Cell(
                                x = columnIndex,
                                y = rowIndex,
                                number = number.toInt(),
                                isMarked = false
                            )
                        }
                })
    }
}

fun List<Board>.mark(number: Int) = map { it.mark(number) }

