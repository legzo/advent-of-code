package gg.jte.aoc.v2023

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day07Test {

    private val input = """
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483
        """.trimIndent()

    @Test
    fun `should parse input as hands`() {
        input.parseAsHands() shouldBe listOf(
            Hand(cards = "32T3K", bid = 765),
            Hand(cards = "T55J5", bid = 684),
            Hand(cards = "KK677", bid = 28),
            Hand(cards = "KTJJT", bid = 220),
            Hand(cards = "QQQJA", bid = 483),
        )
    }

    @Test
    fun `should get hand type`() {
        Hand(cards = "32T3K", bid = 765).type shouldBe HandType.OnePair
        Hand(cards = "T55J5", bid = 684).type shouldBe HandType.ThreeOfAKind
        Hand(cards = "KK677", bid = 28).type shouldBe HandType.TwoPair
        Hand(cards = "KTJJT", bid = 220).type shouldBe HandType.TwoPair
        Hand(cards = "QQQJA", bid = 483).type shouldBe HandType.ThreeOfAKind

        Hand(cards = "QQQQA", bid = 483).type shouldBe HandType.FourOfAKind
        Hand(cards = "QAQQQ", bid = 483).type shouldBe HandType.FourOfAKind

        Hand(cards = "QQQQQ", bid = 483).type shouldBe HandType.FiveOfAKind

        Hand(cards = "QAQQA", bid = 483).type shouldBe HandType.FullHouse
    }

    @Test
    fun `should compare cards of same type`() {
        listOf(
            Hand(cards = "QQQQQ", bid = 483),
            Hand(cards = "22222", bid = 483),
        ).sorted() shouldBe listOf(
            Hand(cards = "22222", bid = 483),
            Hand(cards = "QQQQQ", bid = 483),
        )

        listOf(
            Hand(cards = "32T3K", bid = 765),
            Hand(cards = "T55J5", bid = 684),
            Hand(cards = "KK677", bid = 28),
            Hand(cards = "KTJJT", bid = 220),
            Hand(cards = "QQQJA", bid = 483),
        ).sorted() shouldBe listOf(
            Hand(cards = "32T3K", bid = 765),
            Hand(cards = "KTJJT", bid = 220),
            Hand(cards = "KK677", bid = 28),
            Hand(cards = "T55J5", bid = 684),
            Hand(cards = "QQQJA", bid = 483),
        )
    }

    @Test
    fun `should calculate total winnings`() {
        listOf(
            Hand(cards = "32T3K", bid = 765),
            Hand(cards = "T55J5", bid = 684),
            Hand(cards = "KK677", bid = 28),
            Hand(cards = "KTJJT", bid = 220),
            Hand(cards = "QQQJA", bid = 483),
        ).totalWinnings() shouldBe 6440
    }
}