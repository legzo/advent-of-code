package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import gg.jte.aoc.v2022.CraneType.*
import java.util.*

fun main() {
    val input = getTextFromFile("v2022/day05.txt")
    measureTimeAndPrint { calculateFinalStacksState(input, CRATEMOVER_9000).asMessage() }
    measureTimeAndPrint { calculateFinalStacksState(input, CRATEMOVER_9001).asMessage() }
}

fun List<Stack<Char>>.asMessage() =
    joinToString(separator = "") { if (it.isNotEmpty()) it.pop().toString() else " " }

enum class CraneType { CRATEMOVER_9000, CRATEMOVER_9001 }

fun calculateFinalStacksState(input: String, craneType: CraneType): List<Stack<Char>> {
    val moves = parseMoves(input)
    val stacks = parseStacks(input)

    moves.forEach { (from, to, numberOfCrates) ->
        val stackFrom = stacks[from - 1]
        val stackTo = stacks[to - 1]

        when (craneType) {
            CRATEMOVER_9000 -> moveCratesOneByOne(numberOfCrates = numberOfCrates, from = stackFrom, to = stackTo)
            CRATEMOVER_9001 -> moveMultipleCratesAtOnce(numberOfCrates = numberOfCrates, from = stackFrom, to = stackTo)
        }
    }

    return stacks
}

private fun moveMultipleCratesAtOnce(
    numberOfCrates: Int,
    from: Stack<Char>,
    to: Stack<Char>
) {
    val tempStack = Stack<Char>()
    moveCratesOneByOne(numberOfCrates = numberOfCrates, from = from, to = tempStack)
    moveCratesOneByOne(numberOfCrates = numberOfCrates, from = tempStack, to = to)
}

private fun moveCratesOneByOne(
    numberOfCrates: Int,
    from: Stack<Char>,
    to: Stack<Char>
) = repeat(numberOfCrates) {
    if (!from.empty()) {
        val element = from.pop()
        to.push(element)
    }
}

fun parseStacks(input: String): List<Stack<Char>> {
    val stacksAsString = input.substringBefore("\n\n")
    val numberOfStacks = getNumberOfStacks(stacksAsString)
    val cratesAsString = stacksAsString.lines().dropLast(1).reversed()

    return (1..numberOfStacks).map { index ->
        Stack<Char>()
            .apply {
                cratesAsString
                    .forEach { line ->
                        val col = 4 * (index - 1) + 1
                        if (col < line.length) {
                            val char = line[col]
                            if (char.isLetter()) push(char)
                        }
                    }
            }

    }
}

fun parseMoves(input: String): List<Move> =
    input.substringAfter("\n\n")
        .lines()
        .mapNotNull {
            it.parseWithRegex("move (\\d+) from (\\d+) to (\\d+)") { (numberOfCrates, from, to) ->
                Move(from = from.toInt(), to = to.toInt(), numberOfCrates = numberOfCrates.toInt())
            }
        }


data class Move(
    val from: Int,
    val to: Int,
    val numberOfCrates: Int
)

private fun getNumberOfStacks(stacksAsString: String) = stacksAsString.lines()
    .last()
    .split("   ")
    .maxOf { it.trim().toInt() }


