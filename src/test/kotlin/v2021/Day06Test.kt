package v2021

import gg.jte.aoc.v2021.simulateFish
import gg.jte.aoc.v2021.toFishList
import io.kotest.matchers.collections.shouldHaveSize
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun `should evolve fish`() {
        val initialFishList = listOf(3, 4, 3, 1, 2).toFishList()

        simulateFish(initialList = initialFishList, numberOfDays = 18) shouldHaveSize 26
        simulateFish(initialList = initialFishList, numberOfDays = 80) shouldHaveSize 5934
    }

}