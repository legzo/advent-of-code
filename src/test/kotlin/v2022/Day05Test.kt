package gg.jte.aoc.v2022

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.Stack

internal class Day05Test {

    private val input = """
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
        """.trimIndent()


    @Test
    fun `should parse stacks`() {
        val result = parseStacks(input)

        result shouldHaveSize 3
        result[1] shouldContainExactly Stack<Char>().apply {
            push('M')
            push('C')
            push('D')
        }
    }

    @Test
    fun `should parse moves`() {
        val result = parseMoves(input)

        result shouldHaveSize 4
        result[0] shouldBe Move(numberOfCrates = 1, from = 2, to = 1)
        result[1] shouldBe Move(numberOfCrates = 3, from = 1, to = 3)
    }

    @Test
    fun `should execute moves sequence`() {
        val finalState = calculateFinalStacksState(input, CraneType.CRATEMOVER_9000)
        finalState shouldHaveSize 3
        finalState.asMessage() shouldBe "CMZ"
    }

    @Test
    fun `should execute moves sequence with alternate crane model`() {
        val finalState = calculateFinalStacksState(input, CraneType.CRATEMOVER_9001)
        finalState shouldHaveSize 3
        finalState.asMessage() shouldBe "MCD"
    }

}
