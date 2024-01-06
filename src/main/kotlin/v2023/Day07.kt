package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.v2023.HandType.FiveOfAKind
import gg.jte.aoc.v2023.HandType.FourOfAKind
import gg.jte.aoc.v2023.HandType.FullHouse
import gg.jte.aoc.v2023.HandType.HighCard
import gg.jte.aoc.v2023.HandType.OnePair
import gg.jte.aoc.v2023.HandType.ThreeOfAKind
import gg.jte.aoc.v2023.HandType.TwoPair

fun main() {
    val input = getTextFromFile("v2023/day07.txt")
    measureTimeAndPrint { input.parseAsHands(useJokers = false).totalWinnings() }
    measureTimeAndPrint { input.parseAsHands(useJokers = true).totalWinnings() }
}

data class Hand(
    val cards: String,
    val bid: Int,
    val jokersUsed: Boolean = false,
) : Comparable<Hand> {

    val type: HandType
        get() {
            val jokersCount = if (jokersUsed) cards.count { it == 'J' } else 0
            val shouldConsiderJokers = jokersUsed && jokersCount > 0

            val occurences = cards
                .filter { !shouldConsiderJokers || it != 'J' }
                .groupingBy { it }
                .eachCount()

            val maxOccurences = occurences.values.maxOrNull() ?: 0
            val numberOfPairs = occurences.count { it.value == 2 }

            return when (maxOccurences + jokersCount) {
                5 -> FiveOfAKind
                4 -> FourOfAKind
                3 -> when (numberOfPairs) {
                    2 -> if (shouldConsiderJokers) FullHouse else error("Wtf bro ?")
                    1 -> if (shouldConsiderJokers) ThreeOfAKind else FullHouse
                    else -> ThreeOfAKind
                }

                2 -> when (numberOfPairs) {
                    2 -> TwoPair
                    else -> OnePair
                }

                1 -> HighCard
                else -> error("Wtf bro ?")
            }
        }

    private val asSortKey = this.cards
        .map { if (jokersUsed) it.asDeckCard().sortKeyForJokers else it.asDeckCard().sortKey }
        .joinToString(separator = "")

    override fun compareTo(other: Hand): Int =
        if (type == other.type) this.asSortKey.compareTo(other.asSortKey)
        else type.ordinal.compareTo(other.type.ordinal)

}

fun String.parseAsHands(useJokers: Boolean): List<Hand> =
    lines().map { line ->
        val (cards, bid) = line.split(" ")
        Hand(cards, bid.toInt(), useJokers)
    }

fun List<Hand>.totalWinnings() =
    sorted()
        .foldIndexed(0) { index, acc, hand -> acc + hand.bid * (index + 1) }

enum class HandType {
    HighCard,
    OnePair,
    TwoPair,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind,
}

enum class DeckCard(
    val asString: String,
    val sortKey: String = asString,
    val sortKeyForJokers: String = sortKey
) {
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    T("T", "A"),
    J("J", "B", "1"),
    Q("Q", "C"),
    K("K", "D"),
    A("A", "E"),
}

fun Char.asDeckCard(): DeckCard = DeckCard.entries.first { it.asString == this.toString() }
