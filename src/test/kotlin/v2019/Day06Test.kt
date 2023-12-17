package gg.jte.aoc.v2019

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day06Test {

    private val input = """
        COM)B
        B)C
        C)D
        D)E
        E)F
        B)G
        G)H
        D)I
        E)J
        J)K
        K)L
    """.trimIndent()
        .split("\n")

    @Test
    fun `should parse orbits`() {
        val orbits = input.toOrbits()
        orbits shouldContain ("B" to "COM")
        orbits shouldContain ("L" to "K")
    }

    @Test
    fun `should count orbits`() {
        val orbits = input.toOrbits()
        orbits.countAll() shouldBe 42
    }

    @Test
    fun `should find shortest path`() {
        listOf("K", "J", "E", "D", "C", "B", "COM")
            .minimalOrbitalTransfers(listOf("I", "D", "C", "B", "COM")) shouldBe 4
    }

}
