package gg.jte.aoc.v2021

import gg.jte.aoc.getLinesFromFile

typealias BitFrequency = Pair<Char, Int>

fun main() {
    val input = getLinesFromFile("v2021/day_03.txt")
    println(calculateEpsilonRate(input) * calculateGammaRate(input))
    println(calculateOxygenGeneratorRating(input) * calculateCO2ScrubberRating(input))
}

fun calculateOxygenGeneratorRating(diagnosticReport: List<String>) =
    diagnosticReport.filterBy(::keepMostFrequent).convertToDecimalInt()

fun calculateCO2ScrubberRating(diagnosticReport: List<String>) =
    diagnosticReport.filterBy(::keepLeastFrequent).convertToDecimalInt()

fun calculateGammaRate(diagnosticReport: List<String>): Int =
    diagnosticReport.forEachBit(apply = ::keepMostFrequent).convertToDecimalInt()

fun calculateEpsilonRate(diagnosticReport: List<String>): Int =
    diagnosticReport.forEachBit(apply = ::keepLeastFrequent).convertToDecimalInt()

private fun List<String>.filterBy(
    apply: (Iterable<BitFrequency>) -> String
): String =
    (0 until first().length)
        .fold(this) { remainingList, index ->
            val bitToKeep = remainingList.forEachBit(atIndex = index, apply)
            remainingList.filter { it[index].toString() == bitToKeep }
        }
        .joinToString(separator = "") { it }

private fun List<String>.forEachBit(
    atIndex: Int? = null,
    apply: (Iterable<BitFrequency>) -> String
): String {
    val range = if (atIndex != null) (atIndex until atIndex + 1)
    else (0 until first().length)

    return range
        .joinToString(separator = "") { bitIndex ->
            apply(getBitsFrequencies(this, bitIndex))
        }
}

private fun String.convertToDecimalInt() = toInt(2)

private fun keepMostFrequent(bitsFrequencies: Iterable<BitFrequency>): String {
    val topFrequencies = bitsFrequencies
        .groupBy { (_, numberOfOccurrences) -> numberOfOccurrences }
        .maxByOrNull { (numberOfOccurrences, _) -> numberOfOccurrences }

    return if (topFrequencies?.value?.size == 1) topFrequencies.value.first().first.toString()
    else "1"
}

private fun keepLeastFrequent(bitsFrequencies: Iterable<BitFrequency>): String {
    val lowestFrequencies = bitsFrequencies
        .groupBy { (_, numberOfOccurrences) -> numberOfOccurrences }
        .minByOrNull { (numberOfOccurrences, _) -> numberOfOccurrences }

    return if (lowestFrequencies?.value?.size == 1) lowestFrequencies.value.first().first.toString()
    else "0"
}

private fun getBitsFrequencies(
    diagnosticReport: List<String>,
    bitIndex: Int
): List<Pair<Char, Int>> =
    diagnosticReport
        .map { diagnosticLine -> diagnosticLine[bitIndex] }
        .groupingBy { it }
        .eachCount()
        .toList()
