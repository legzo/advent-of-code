package gg.jte.aoc.v2019

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    @Test
    fun `should parse layers`() {
        "123456789012".toLayers(3, 2) shouldBe listOf(
            Layer("123456", width = 3, height = 2),
            Layer("789012", width = 3, height = 2)
        )
    }

    @Test
    fun `should find interesting layer`() {
        listOf(Layer("123456", 3, 2), Layer("789012", 3, 2))
            .findInterestingLayer() shouldBe Layer("123456", 3, 2)
    }

    @Test
    fun `should calculate checksum`() {
        Layer("123456", 3, 2).checksum shouldBe 1
    }

    @Test
    fun `should merge layers`() {
        Layer("0222", 2, 2).mergeWith(Layer("1122", 2, 2)) shouldBe Layer("0122", 2, 2)
        Layer("0122", 2, 2).mergeWith(Layer("2212", 2, 2)) shouldBe Layer("0112", 2, 2)
        Layer("0112", 2, 2).mergeWith(Layer("0000", 2, 2)) shouldBe Layer("0110", 2, 2)
    }

    @Test
    fun `should merge all layers`() {
        listOf(
            Layer("0222", 2, 2),
            Layer("1122", 2, 2),
            Layer("2212", 2, 2),
            Layer("0000", 2, 2)
        ).mergeAll() shouldBe Layer("0110", 2, 2)
    }
}





