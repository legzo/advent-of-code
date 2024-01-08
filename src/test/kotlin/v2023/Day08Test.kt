package gg.jte.aoc.v2023

import gg.jte.aoc.v2023.Instruction.LEFT
import gg.jte.aoc.v2023.Instruction.RIGHT
import io.kotest.matchers.sequences.shouldHaveSize
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
        input.parseAsDocument().followInstructions() shouldHaveSize 2
        input2.parseAsDocument().followInstructions() shouldHaveSize 6
    }
}