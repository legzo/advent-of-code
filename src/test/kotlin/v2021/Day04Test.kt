package v2021

import gg.jte.aoc.v2021.Board
import gg.jte.aoc.v2021.Cell
import gg.jte.aoc.v2021.calculateWinningScore
import gg.jte.aoc.v2021.mark
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day04Test {

    @Test
    fun `should get winning score`() {
        val boards = listOf(
            Board.from(
                listOf(
                    "22 13 17 11  0",
                    "8  2 23  4 24",
                    "21  9 14 16  7",
                    "6 10  3 18  5",
                    "1 12 20 15 19",
                )
            ),
            Board.from(
                listOf(
                    "3 15  0  2 22",
                    "9 18 13 17  5",
                    "19  8  7 25 23",
                    "20 11 10 24  4",
                    "14 21 16 12  6",
                )
            ),
            Board.from(
                listOf(
                    "14 21 17 24  4",
                    "10 16 15  9 19",
                    "18  8 23 26 20",
                    "22 11 13  6  5",
                    "2  0 12  3  7",
                )
            )
        )

        val drawNumbers = listOf(7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1)

        calculateWinningScore(boards, drawNumbers) shouldBe 4512
    }

    @Test
    fun `should parse board`() {

        val board = Board.from(
            listOf(
                "22 13 17 11  0",
                " 8  2 23  4 24",
                "21  9 14 16  7",
                " 6 10  3 18  5",
                " 1 12 20 15 19"
            )
        )

        board.cells[0] shouldBe Cell(x = 0, y = 0, number = 22, isMarked = false)
        board.cells[1] shouldBe Cell(x = 1, y = 0, number = 13, isMarked = false)

        board.at(x = 3, y = 3) shouldBe Cell(x = 3, y = 3, number = 18, isMarked = false)
    }

    @Test
    fun `should mark number in board`() {
        val board = Board(
            cells = listOf(
                Cell(x = 0, y = 0, number = 22, isMarked = false),
                Cell(x = 0, y = 1, number = 13, isMarked = false),
                Cell(x = 1, y = 0, number = 8, isMarked = false),
                Cell(x = 1, y = 1, number = 2, isMarked = false)
            )
        )

        listOf(board).mark(829) shouldBe listOf(board)

        listOf(board).mark(8) shouldBe listOf(
            Board(
                cells = listOf(
                    Cell(x = 0, y = 0, number = 22, isMarked = false),
                    Cell(x = 0, y = 1, number = 13, isMarked = false),
                    Cell(x = 1, y = 0, number = 8, isMarked = true),
                    Cell(x = 1, y = 1, number = 2, isMarked = false)
                )
            )
        )
    }

    @Test
    fun `should mark as won`() {
        Board(
            cells = listOf(
                Cell(x = 0, y = 0, number = 22, isMarked = false),
                Cell(x = 0, y = 1, number = 13, isMarked = false),
                Cell(x = 1, y = 0, number = 8, isMarked = true),
                Cell(x = 1, y = 1, number = 2, isMarked = false)
            )
        ).wins() shouldBe false

        Board(
            cells = listOf(
                Cell(x = 0, y = 0, number = 22, isMarked = false),
                Cell(x = 0, y = 1, number = 13, isMarked = false),
                Cell(x = 1, y = 0, number = 8, isMarked = true),
                Cell(x = 1, y = 1, number = 2, isMarked = true)
            )
        ).wins() shouldBe true

        Board(
            cells = listOf(
                Cell(x = 0, y = 0, number = 22, isMarked = true),
                Cell(x = 0, y = 1, number = 13, isMarked = false),
                Cell(x = 1, y = 0, number = 8, isMarked = true),
                Cell(x = 1, y = 1, number = 2, isMarked = false)
            )
        ).wins() shouldBe true
    }

}