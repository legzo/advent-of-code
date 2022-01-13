package v2021

import gg.jte.aoc.v2021.CostStrategies
import gg.jte.aoc.v2021.findMinimumFuelAmountRequired
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day07Test {

    @Test
    fun `should find minimum fuel amount required with constant cost`() {
        val offsets = listOf(16,1,2,0,4,2,7,1,2,14)
        findMinimumFuelAmountRequired(offsets, CostStrategies::constantCost) shouldBe 37
    }

    @Test
    fun `should find minimum fuel amount required with linear increasing cost`() {
        val offsets = listOf(16,1,2,0,4,2,7,1,2,14)
        findMinimumFuelAmountRequired(offsets, CostStrategies::linearIncreasingCost) shouldBe 168
    }
}