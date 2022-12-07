package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2022/day03.txt")
    measureTimeAndPrint { prioritiesOfMisplacedItems(input).sum() }
    measureTimeAndPrint { prioritiesOfBadges(input).sum() }
}

fun prioritiesOfMisplacedItems(input: String): List<Int> =
    input
        .lines()
        .map(::findCommonItem)
        .map(Char::toPriority)

fun prioritiesOfBadges(input: String): List<Int> =
    input
        .lines()
        .chunked(3)
        .map(::findBadge)
        .map(Char::toPriority)

private fun findBadge(elvesRucksacks: List<String>): Char =
    elvesRucksacks.first()
        .first { char ->  elvesRucksacks.all { it.contains(char) } }

private fun findCommonItem(it: String): Char {
    val (firstCompartment, secondCompartment) = splitCompartments(it)
    return firstCompartment.intersect(secondCompartment).first()
}

private fun splitCompartments(it: String): Pair<Set<Char>, Set<Char>> {
    val midSize = it.length / 2
    val firstCompartment = it.take(midSize).toSet()
    val secondCompartment = it.takeLast(midSize).toSet()
    return firstCompartment to secondCompartment
}

private fun Char.toPriority() = if (isUpperCase()) code - 38 else code - 96