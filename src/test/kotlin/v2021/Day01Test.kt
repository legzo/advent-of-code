package v2021

import gg.jte.aoc.v2021.countIncreasingStepsWithSlidingWindow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun `should count the number of increasing steps`() {
        countIncreasingStepsWithSlidingWindow(
            windowSize = 1,
            measures = listOf(
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
    fun `should count the number of increasing steps with sliding window`() {
        countIncreasingStepsWithSlidingWindow(
            windowSize = 3,
            measures = listOf(
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