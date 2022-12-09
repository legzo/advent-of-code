package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import kotlin.math.max

fun main() {
    val input = getTextFromFile("v2022/day04.txt")
    measureTimeAndPrint { countFullyOverlappingRanges(input) }
    measureTimeAndPrint { countOverlappingRanges(input) }
}

fun countFullyOverlappingRanges(input: String) =
    parseSectionsPairs(input)
        .count { it.first fullyOverlaps it.second }

fun countOverlappingRanges(input: String) =
    parseSectionsPairs(input)
        .count { it.first overlaps it.second }

private fun parseSectionsPairs(input: String) =
    input
        .lines()
        .mapNotNull(::toPairOfSetsOfInts)

private infix fun Set<Int>.fullyOverlaps(second: Set<Int>) =
    (this + second).size == max(this.size, second.size)

private infix fun Set<Int>.overlaps(second: Set<Int>) =
    (this intersect second).isNotEmpty()

private fun toPairOfSetsOfInts(it: String) =
    it.parseWithRegex("(\\d+)-(\\d+),(\\d+)-(\\d+)") { (min1, max1, min2, max2) ->
        setOfIntsBetween(min1, max1) to setOfIntsBetween(min2, max2)
    }

private fun setOfIntsBetween(min1: String, max1: String) =
    (min1.toInt()..max1.toInt()).toSet()
