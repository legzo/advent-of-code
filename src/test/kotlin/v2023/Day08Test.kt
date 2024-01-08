package gg.jte.aoc.v2023

import gg.jte.aoc.v2023.Instruction.LEFT
import gg.jte.aoc.v2023.Instruction.RIGHT
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day08Test {

    private val input = """
        RL

        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    private val input2 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    private val input3 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    @Test
    fun `should parse document`() {
        input.parseAsDocument() shouldBe Document(
            instructions = listOf(RIGHT, LEFT),
            nodes = mapOf(
                "AAA" to Node(id="AAA", left="BBB", right="CCC"),
                "BBB" to Node(id="BBB", left="DDD", right="EEE"),
                "CCC" to Node(id="CCC", left="ZZZ", right="GGG"),
                "DDD" to Node(id="DDD", left="DDD", right="DDD"),
                "EEE" to Node(id="EEE", left="EEE", right="EEE"),
                "GGG" to Node(id="GGG", left="GGG", right="GGG"),
                "ZZZ" to Node(id="ZZZ", left="ZZZ", right="ZZZ")
            )
        )

        input2.parseAsDocument() shouldBe Document(
            instructions = listOf(LEFT, LEFT, RIGHT),
            nodes = mapOf(
                "AAA" to Node("AAA", "BBB", "BBB"),
                "BBB" to Node("BBB", "AAA", "ZZZ"),
                "ZZZ" to Node("ZZZ", "ZZZ", "ZZZ")
            )
        )
    }

    @Test
    fun `should follow instructions`() {
        input.parseAsDocument().countIterationsForHumans() shouldBe 2
        input2.parseAsDocument().countIterationsForHumans() shouldBe  6
    }

    @Test
    fun `should get all starting nodes`() {
        input3.parseAsDocument().startingNodes shouldBe listOf(
            Node(id = "11A", left = "11B", right = "XXX"),
            Node(id = "22A", left = "22B", right = "XXX"),
        )
    }

    @Test
    fun `should follow instructions for ghosts`() {
        input3.parseAsDocument().countIterationsForGhosts() shouldBe 6
    }
}