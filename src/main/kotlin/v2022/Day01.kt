package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2022/day01.txt")
    measureTimeAndPrint {  findMostCaloriesCarriedByAnElf(input) }
}


fun findMostCaloriesCarriedByAnElf(input: String): Int =
    input
        .trim()
        .split("\n\n")
        .maxOfOrNull { it.split("\n").sumOf(String::toInt) }
        ?: 0

