package gg.jte.aoc.v2023

import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day03Test {

    private val input = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...@.*....
        .664.598..
        """.trimIndent()

    private val input2 = """
        12.......*..
        +.........34
        .......-12..
        ..78........
        ..*....60..1
        78.........9
        .5.....23..+
        8...90*12...
        ............
        2.2......12.
        .*.........*
        1.1..503+.56
        """.trimIndent()

    @Test
    fun `locate symbols`() {
        input.parse()
            .symbols
            .map { it.point }
            .shouldContainAll(
                Point(x = 3, y = 1),
                Point(x = 6, y = 3),
                Point(x = 3, y = 4),
                Point(x = 5, y = 5),
                Point(x = 3, y = 8),
                Point(x = 5, y = 8),
            )
    }

    @Test
    fun `locate numbers`() {
        input.parse().numbers.shouldContainAll(
            Number(point = Point(x = 0, y = 0), value = "467"),
            Number(point = Point(x = 2, y = 2), value = "35"),
            Number(point = Point(x = 6, y = 2), value = "633"),
        )
    }

    @Test
    fun `identify part numbers`() {
        val partNumbers = input.parse().partNumbers

        partNumbers
            .shouldHaveSize(8)
            .shouldNotContainAll(
                Number(point = Point(x = 5, y = 0), value = "114"),
                Number(point = Point(x = 7, y = 5), value = "58"),
            )

        partNumbers.shouldContainAll(
            Number(point = Point(x = 0, y = 0), value = "467"),
            Number(point = Point(x = 2, y = 2), value = "35"),
            Number(point = Point(x = 6, y = 2), value = "633"),
        )

        input.parse()
            .partNumbers
            .sumOf { it.value.toInt() } shouldBe 4361

        input2.parse()
            .partNumbers
            .sumOf { it.value.toInt() } shouldBe 925
    }


}
