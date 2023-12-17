package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day06Test {

    @Test
    fun `should count questions answered with yes by at least someone in a group`() {
        Group(results = listOf("abc")).numberOfQuestionsAnsweredWithYesByAtLeastOnePerson shouldBe 3
        Group(results = listOf("a", "b", "c")).numberOfQuestionsAnsweredWithYesByAtLeastOnePerson shouldBe 3
        Group(results = listOf("ab", "ac")).numberOfQuestionsAnsweredWithYesByAtLeastOnePerson shouldBe 3
        Group(results = listOf("a", "a", "a", "a")).numberOfQuestionsAnsweredWithYesByAtLeastOnePerson shouldBe 1
        Group(results = listOf("b")).numberOfQuestionsAnsweredWithYesByAtLeastOnePerson shouldBe 1
    }

    @Test
    fun `should count questions answered with yes by everyone in a group`() {
        Group(results = listOf("abc")).numberOfQuestionsAnsweredWithYesByEveryone shouldBe 3
        Group(results = listOf("a", "b", "c")).numberOfQuestionsAnsweredWithYesByEveryone shouldBe 0
        Group(results = listOf("ab", "ac")).numberOfQuestionsAnsweredWithYesByEveryone shouldBe 1
        Group(results = listOf("a", "a", "a", "a")).numberOfQuestionsAnsweredWithYesByEveryone shouldBe 1
        Group(results = listOf("b")).numberOfQuestionsAnsweredWithYesByEveryone shouldBe 1
    }

    @Test
    fun `should parse groups from string`() {
        val input = """
            abc

            a
            b
            c

            ab
            ac

            a
            a
            a
            a

            b
        """.trimIndent()

        input.parseGroups() shouldBe listOf(
            Group(results = listOf("abc")),
            Group(results = listOf("a", "b", "c")),
            Group(results = listOf("ab", "ac")),
            Group(results = listOf("a", "a", "a", "a")),
            Group(results = listOf("b"))
        )
    }

}
