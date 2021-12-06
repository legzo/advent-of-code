package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile

typealias BitFrequency = Pair<Char, Int>

fun main() {
    val input = getLinesFromFile("v2021/day_03.txt")
    println(calculateEpsilonRate(input) * calculateGammaRate(input))
}

fun calculateGammaRate(diagnosticReport: List<String>): Int =
    diagnosticReport.forEachBit(::keepMostFrequent).convertToInt()

fun calculateEpsilonRate(diagnosticReport: List<String>): Int =
    diagnosticReport.forEachBit(::keepLessFrequent).convertToInt()

fun List<String>.forEachBit(
    funToApply: (Iterable<BitFrequency>) -> BitFrequency?
): String =
    (0 until first().length)
        .joinToString(separator = "") { bitIndex ->
            funToApply(getBitsFrequencies(this, bitIndex))
                ?.first.toString()
        }

private fun String.convertToInt() = toInt(2)

private fun keepMostFrequent(bitsFrequencies: Iterable<BitFrequency>) =
    bitsFrequencies.maxByOrNull { (_, numberOfOccurrences) -> numberOfOccurrences }

private fun keepLessFrequent(bitsFrequencies: Iterable<BitFrequency>) =
    bitsFrequencies.minByOrNull { (_, numberOfOccurrences) -> numberOfOccurrences }

private fun getBitsFrequencies(
    diagnosticReport: List<String>,
    bitIndex: Int
): List<Pair<Char, Int>> =
    diagnosticReport
        .map { diagnosticLine -> diagnosticLine[bitIndex] }
        .groupingBy { it }
        .eachCount()
        .toList()
