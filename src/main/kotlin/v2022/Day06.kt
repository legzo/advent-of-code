package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2022/day06.txt")
    measureTimeAndPrint { findFirstMarkerIndex(input) }
    measureTimeAndPrint { findFirstMessageIndex(input) }
}

fun findFirstMessageIndex(input: String): Int = findFirstIndividualCharsWindowIndex(input, windowSize = 14)

fun findFirstMarkerIndex(input: String): Int = findFirstIndividualCharsWindowIndex(input, windowSize = 4)

fun findFirstIndividualCharsWindowIndex(input: String, windowSize: Int): Int =
    input
        .windowed(windowSize)
        .indexOfFirst { it.toSet().size == windowSize }
        .plus(windowSize)