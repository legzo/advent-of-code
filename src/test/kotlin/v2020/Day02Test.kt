package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    @Test
    fun `should find compliant passwords for old policy`() {
        listOf(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc"
        ).findCompliantPasswords(PasswordPolicy.Old) shouldBe listOf(
            "abcde",
            "ccccccccc"
        )
    }

    @Test
    fun `should count compliant passwords for old policy`() {
        listOf(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc"
        ).countCompliantPasswords(PasswordPolicy.Old) shouldBe 2
    }

    @Test
    fun `should find compliant passwords for new policy`() {
        listOf(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc"
        ).findCompliantPasswords(PasswordPolicy.New) shouldBe listOf("abcde")
    }

    @Test
    fun `should count compliant passwords for new policy`() {
        listOf(
            "1-3 a: abcde",
            "1-3 b: cdefg",
            "2-9 c: ccccccccc"
        ).countCompliantPasswords(PasswordPolicy.New) shouldBe 1
    }

}


