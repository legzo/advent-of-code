package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import kotlin.math.abs

fun main() {
    val input = getTextFromFile("v2024/day01.txt")
    measureTimeAndPrint { parseLists(input).calculateTotalDistance() }
    measureTimeAndPrint { parseLists(input).calculateSimilarityScore() }
}

fun parseLists(input: String): Pair<List<Int>, List<Int>> =
    input
        .lines()
        .mapNotNull {
            it.parseWithRegex("(\\d+)\\s+(\\d+)") { (first, second) -> first.toInt() to second.toInt() }
        }
        .unzip()

fun Pair<List<Int>, List<Int>>.calculateTotalDistance(): Int =
    first.sorted()
        .zip(second.sorted())
        .sumOf { (f, s) -> abs(f - s) }

fun Pair<List<Int>, List<Int>>.calculateSimilarityScore(): Int {
    val referenceMap = second.groupingBy { it }.eachCount()
    return first.sumOf { it * (referenceMap[it] ?: 0) }
}