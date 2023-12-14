package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import kotlin.math.pow

fun main() {
    val input = getTextFromFile("v2023/day04.txt")
    measureTimeAndPrint { input.parseAsCards().calculateScores().sum() }
    measureTimeAndPrint {
        input.parseAsCards()
            .process()
            .countAllCopies()
    }
}

data class Card(val id: Int, val winningNumbers: List<Int>, val drawnNumbers: List<Int>)
data class CopiedCard(val card: Card, val nbOfCopies: Int)

fun String.parseAsCards(): List<Card> =
    lines().map {
        val (cardIdAsString, winningNumbers, drawnNumbers) = it.split(Regex("[:|]"))
        Card(
            id = cardIdAsString.removePrefix("Card ").trim().toInt(),
            winningNumbers = toInts(winningNumbers),
            drawnNumbers = toInts(drawnNumbers)
        )
    }

fun Card.findMatchingNumbers(): List<Int> =
    drawnNumbers.filter { it in winningNumbers }

fun Card.score(): Int =
    findMatchingNumbers().count()

fun List<Card>.calculateScores(): List<Int> =
    map { 2f.pow(it.score() - 1).toInt() }

fun List<Card>.process(): Collection<CopiedCard> =
    fold(initialCopiesMap()) { stackOfCards, currentCard ->
        val nbOfCopiesToCreate = stackOfCards[currentCard.id]?.nbOfCopies
            ?: error("no card with id ${currentCard.id}")

        val nbOfCardsToCopy = currentCard.score()

        stackOfCards.mapValues { (cardId, cardToUpdate) ->
            if (cardId in currentCard.next(nbOfCardsToCopy))
                cardToUpdate.copyNTimes(n = nbOfCopiesToCreate)
            else cardToUpdate
        }

    }.values

private fun CopiedCard.copyNTimes(n: Int): CopiedCard =
    copy(card = card, nbOfCopies = this.nbOfCopies + n)

private fun Card.next(nbOfCards: Int): IntRange = this.id + 1..(this.id + nbOfCards)

private fun List<Card>.initialCopiesMap() =
    associate { it.id to CopiedCard(it, 1) }

fun Collection<CopiedCard>.countAllCopies(): Int = sumOf { it.nbOfCopies }

private fun toInts(winningNumbers: String): List<Int> =
    winningNumbers
        .trim()
        .split(Regex("\\s+"))
        .map(String::toInt)

