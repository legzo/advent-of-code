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
        val (rules, updates) = parseAsRulesAndUpdate(input)

        rules shouldHaveSize 21
        updates shouldHaveSize 6
    }

    @Test
    fun `should check rule`() {
        Rule(beforePage = 12, afterPage = 75) isValidFor Update(12, 34, 75) shouldBe true
        Rule(beforePage = 3, afterPage = 75) isValidFor Update(12, 34, 75) shouldBe true
        Rule(beforePage = 12, afterPage = 78) isValidFor Update(12, 34, 75) shouldBe true

        Rule(beforePage = 75, afterPage = 12) isValidFor Update(12, 34, 75) shouldBe false
        Rule(beforePage = 75, afterPage = 34) isValidFor Update(12, 34, 75) shouldBe false
        Rule(beforePage = 34, afterPage = 12) isValidFor Update(12, 34, 75) shouldBe false
    }

    @Test
    fun `should get all valid updates`() {
        val rulesAndUpdates = parseAsRulesAndUpdate(input)
        rulesAndUpdates.getValidUpdates() shouldHaveSize 3
    }

    @Test
    fun `should calculate sum of middle page numbers`() {
        val rulesAndUpdates = parseAsRulesAndUpdate(input)
        rulesAndUpdates.getValidUpdates().sumOfMiddlePageNumbers() shouldBe 143
    }
}