package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day01.txt")
    measureTimeAndPrint { input.findCalibrationValues().sum() }
    measureTimeAndPrint { input.rectifyInput().findCalibrationValues().sum() }
}

fun String.findCalibrationValues() =
    lines()
        .map { line ->
            10 * line.first(Char::isDigit).digitToInt() + line.last(Char::isDigit).digitToInt()
        }

fun String.rectifyInput() = this
    .lines()
    .joinToString("\n") { it.replaceLiteralDigits() }

fun String.replaceLiteralDigits(): String {
    val digitToReplace: Digits? = Digits.entries
        .flatMap { digit -> indicesOf(digit.literal).map { it to digit } }
        .minByOrNull { (index, _) -> index }
        ?.second

    return if (digitToReplace == null) this
    else this.replaceFirst(digitToReplace.literal, digitToReplace.asString)
        .replaceLiteralDigits()
}

enum class Digits(val literal: String, val asString: String) {
    ONE("one", "1e"),
    TWO("two", "2o"),
    THREE("three", "3e"),
    FOUR("four", "4"),
    FIVE("five", "5e"),
    SIX("six", "6"),
    SEVEN("seven", "7n"),
    EIGHT("eight", "8t"),
    NINE("nine", "9"),
}

fun CharSequence.indicesOf(input: String): Sequence<Int> =
    Regex(input)
        .findAll(this)
        .map { it.range.first }

