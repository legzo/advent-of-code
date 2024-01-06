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

    private val input2 = """
        2345A 1
        Q2KJJ 13
        Q2Q2Q 19
        T3T3J 17
        T3Q33 11
        2345J 3
        J345A 2
        32T3K 5
        T55J5 29
        KK677 7
        KTJJT 34
        QQQJA 31
        JJJJJ 37
        JAAAA 43
        AAAAJ 59
        AAAAA 61
        2AAAA 23
        2JJJJ 53
        JJJJ2 41
    """.trimIndent()

    @Test
    fun `should parse input as hands`() {
        input.parseAsHands(false) shouldBe listOf(
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

        listOf(
            Hand(cards = "QQQQ2", bid = 483),
            Hand(cards = "2222Q", bid = 483),
            Hand(cards = "JKKK2", bid = 483),
        ).sorted() shouldBe listOf(
            Hand(cards = "JKKK2", bid = 483),
            Hand(cards = "2222Q", bid = 483),
            Hand(cards = "QQQQ2", bid = 483),
        )
    }

    @Test
    fun `should calculate total winnings`() {
        input.parseAsHands(useJokers = false).totalWinnings() shouldBe 6440
    }

    @Test
    fun `should find type of card using jokers`() {
        Hand(cards = "32T3K", bid = 765, jokersUsed = true).type shouldBe HandType.OnePair
        Hand(cards = "T55J5", bid = 684, jokersUsed = true).type shouldBe HandType.FourOfAKind
        Hand(cards = "KK677", bid = 28, jokersUsed = true).type shouldBe HandType.TwoPair
        Hand(cards = "KTJJT", bid = 220, jokersUsed = true).type shouldBe HandType.FourOfAKind
        Hand(cards = "QQQJA", bid = 483, jokersUsed = true).type shouldBe HandType.FourOfAKind
        Hand(cards = "28JKK", bid = 483, jokersUsed = true).type shouldBe HandType.ThreeOfAKind
        Hand(cards = "33J3J", bid = 483, jokersUsed = true).type shouldBe HandType.FiveOfAKind
        Hand(cards = "T3T3J", bid = 483, jokersUsed = true).type shouldBe HandType.FullHouse
        Hand(cards = "Q2Q2Q", bid = 483, jokersUsed = true).type shouldBe HandType.FullHouse
    }

    @Test
    fun `should calculate total winnings with jokers`() {
        //input.parseAsHands(useJokers = true).totalWinnings() shouldBe 5905
        input2.parseAsHands(useJokers = true).totalWinnings() shouldBe 6839
    }
}