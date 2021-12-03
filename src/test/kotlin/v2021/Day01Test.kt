package v2021

import gg.jte.aoc.v2021.countDecreaseSteps
import gg.jte.aoc.v2021.countDecreaseStepsWithSlidingWindow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `should count the number of decreasing steps`() {
        countDecreaseSteps(
            listOf(
                199,
                200,
                208,
                210,
                200,
                207,
                240,
                269,
                260,
                263
            )
        ) shouldBe 7
    }

    @Test
    fun `should count the number of decreasing steps with sliding window`() {
        countDecreaseStepsWithSlidingWindow(
            listOf(
                199,
                200,
                208,
                210,
                200,
                207,
                240,
                269,
                260,
                263
            )
        ) shouldBe 5
    }

}