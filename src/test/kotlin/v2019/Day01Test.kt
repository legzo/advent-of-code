package gg.jte.aoc.v2019

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day01Test {

    @Test
    fun `should return fuel needed for module mass`() {
        Module(mass = 12).calculateFuelNeeded() shouldBe 2
        Module(mass = 14).calculateFuelNeeded() shouldBe 2
        Module(mass = 1969).calculateFuelNeeded() shouldBe 654
        Module(mass = 100756).calculateFuelNeeded() shouldBe 33583
    }

    @Test
    fun `should return fuel needed for spacecraft`() {
        Spacecraft(
            modules = listOf(
                Module(mass = 12),
                Module(mass = 14),
                Module(mass = 1969),
                Module(mass = 100756)
            )
        ).calculateFuelNeeded() shouldBe 2 + 2 + 654 + 33583
    }

    @Test
    fun `should return total fuel needed for module mass`() {
        Module(mass = 14).calculateTotalFuelNeeded() shouldBe 2
        Module(mass = 1969).calculateTotalFuelNeeded() shouldBe 966
        Module(mass = 100756).calculateTotalFuelNeeded() shouldBe 50346
    }

}
