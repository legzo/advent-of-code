package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2022/day01.txt")
    measureTimeAndPrint {  findMostCaloriesCarriedByAnElf(input) }
    measureTimeAndPrint {  findCaloriesCarriedByTop3Elves(input) }
}

fun findMostCaloriesCarriedByAnElf(input: String): Int? =
    elvesSortedByCarriedCalories(input).maxOrNull()


fun findCaloriesCarriedByTop3Elves(input: String): Int =
    elvesSortedByCarriedCalories(input).take(3).sum()

fun elvesSortedByCarriedCalories(input: String): List<Int> =
    input
        .trim()
        .split("\n\n")
        .map { it.lines().sumOf(String::toInt) }
        .sortedDescending()
