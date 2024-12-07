package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day03.txt")
    measureTimeAndPrint { input.extractMulInstructions().execute() }
}

data class MulInstruction(
    val first: Int,
    val second: Int,
) {
    val result
        get() = first * second
}

fun Collection<MulInstruction>.execute() =
    sumOf { it.result }

fun String.extractMulInstructions(): Collection<MulInstruction> =
    Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        .findAll(this)
        .map { it.destructured }
        .map { (first, second) -> MulInstruction(first.toInt(), second.toInt()) }
        .toList()