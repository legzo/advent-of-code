package gg.jte.aoc.v2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day12Test {

    @Test
    fun `should get spring conditions`() {
        Row(".#.###.#.###### 1,3,1,6").conditions shouldBe listOf(1, 3, 1, 6)
    }

    @Test
    fun `should count valid arrangements for one row`() {
        Row("???.### 1,1,3").nbOfValidArrangements shouldBe 1
        Row(".??..??...?##. 1,1,3").nbOfValidArrangements shouldBe 4
        Row("?#?#?#?#?#?#?#? 1,3,1,6").nbOfValidArrangements shouldBe 1
        Row("????.#...#... 4,1,1").nbOfValidArrangements shouldBe 1
        Row("????.######..#####. 1,6,5").nbOfValidArrangements shouldBe 4
        Row("?###???????? 3,2,1").nbOfValidArrangements shouldBe 10
    }

    @Test
    fun `should get total nb of arrangements`() {
        val input = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
        """.trimIndent()

        input.totalNbOfValidArrangements() shouldBe 21
    }

}