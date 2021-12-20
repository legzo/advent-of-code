package v2021

import gg.jte.aoc.v2021.simulate
import gg.jte.aoc.v2021.toFishCensus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun `should evolve fish`() {
        val fishCensus = listOf(3, 4, 3, 1, 2).toFishCensus()
        simulate(initialCensus = fishCensus, numberOfDays = 18) shouldBe  26
        simulate(initialCensus = fishCensus, numberOfDays = 80) shouldBe  5934
        simulate(initialCensus = fishCensus, numberOfDays = 256) shouldBe 26984457539
    }

}