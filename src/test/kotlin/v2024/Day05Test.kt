package gg.jte.aoc.v2024

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day05Test {

    private val input = """
    47|53
    97|13
    97|61
    97|47
    75|29
    61|13
    75|53
    29|13
    97|29
    53|29
    61|53
    97|53
    61|29
    47|13
    75|47
    97|75
    47|61
    75|61
    47|29
    75|13
    53|13
    
    75,47,61,53,29
    97,61,53,29,13
    75,29,13
    75,97,47,61,53
    61,13,29
    97,13,75,29,47
    """.trimIndent()

    @Test
    fun `should parse input`() {
        val (updates, rules) = parseAsRulesAndUpdate(input)

        rules shouldHaveSize 21
        updates shouldHaveSize 6
    }

    @Test
    fun `should check rule`() {
        Rule(beforePage = 12, afterPage = 75) isNotValidFor Update(12, 34, 75) shouldBe false
        Rule(beforePage = 3, afterPage = 75) isNotValidFor Update(12, 34, 75) shouldBe false
        Rule(beforePage = 12, afterPage = 78) isNotValidFor Update(12, 34, 75) shouldBe false

        Rule(beforePage = 75, afterPage = 12) isNotValidFor Update(12, 34, 75) shouldBe true
        Rule(beforePage = 75, afterPage = 34) isNotValidFor Update(12, 34, 75) shouldBe true
        Rule(beforePage = 34, afterPage = 12) isNotValidFor Update(12, 34, 75) shouldBe true
    }

    @Test
    fun `should get all valid updates`() {
        val (updates, rules) = parseAsRulesAndUpdate(input)
        updates.filter(isValid = true, accordingTo = rules) shouldHaveSize 3
    }

    @Test
    fun `should calculate sum of middle page numbers`() {
        val (updates, rules) = parseAsRulesAndUpdate(input)
        updates.filter(isValid = true, accordingTo = rules).sumOfMiddlePageNumbers() shouldBe 143
    }

    @Test
    fun `should get all invalid updates`() {
        val (updates, rules) = parseAsRulesAndUpdate(input)
        updates.filter(isValid = false, accordingTo = rules) shouldHaveSize 3
    }

    @Test
    fun `should swap elements in updates`() {
        val update = Update(12, 34, 75)
        update.pages.swapElementsAccordingTo(Rule(12, 75)) shouldBe listOf(75, 34, 12)
        update.pages.swapElementsAccordingTo(Rule(75, 12)) shouldBe listOf(75, 34, 12)
        update.pages.swapElementsAccordingTo(Rule(75, 34)) shouldBe listOf(12, 75, 34)
    }

    @Test
    fun `should reorder pages`() {
        val (_, rules) = parseAsRulesAndUpdate(input)
        Update(75, 97, 47, 61, 53).reorderAccordingTo(rules) shouldBe Update(97, 75, 47, 61, 53)
        Update(61, 13, 29).reorderAccordingTo(rules) shouldBe Update(61, 29, 13)
        Update(97, 13, 75, 29, 47).reorderAccordingTo(rules) shouldBe Update(97, 75, 47, 29, 13)
    }
}