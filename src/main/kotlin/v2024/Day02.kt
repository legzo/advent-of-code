package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day02.txt")
    measureTimeAndPrint { input.toReports().count { it.isSafe() } }
    measureTimeAndPrint { input.toReports().count { it.allVariants().any { it.isSafe() } } }
}

typealias Report = List<Int>

fun String.toReports(): List<Report> =
    lines().map { it.split(" ").map { it.toInt() } }

fun Report.isSafe(): Boolean =
    with(zipWithNext().map { (a, b) -> b - a }) {
        all { it in 1..3 } || all { it in -3..-1 }
    }

fun Report.allVariants() =
    (0 until size)
        .map { index -> subList(0, index) + subList(index + 1, size) }
