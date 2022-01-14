package v2021

import gg.jte.aoc.v2021.HeightMap
import gg.jte.aoc.v2021.HeightMapPoint
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day09Test {

    private val heightMap = HeightMap.from(
        listOf(
            "2199943210",
            "3987894921",
            "9856789892",
            "8767896789",
            "9899965678"
        )
    )

    @Test
    fun `should find low points`() {
        heightMap.lowPoints shouldBe listOf(
            HeightMapPoint(1, 0, 1),
            HeightMapPoint(9, 0, 0),
            HeightMapPoint(2, 2, 5),
            HeightMapPoint(6, 4, 5)
        )
    }

    @Test
    fun `should calculate sum of risk levels`() {
        heightMap.sumOfRiskLevels shouldBe 15
    }

}