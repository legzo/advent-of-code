package gg.jte.aoc.v2021

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2021/day_06.txt").split(",").map { Lanternfish(it.toInt()) }

    measureTimeAndPrint { simulateFish(initialList = input, numberOfDays = 80).size }
}

fun List<Int>.toFishList() =
    map { Lanternfish(it) }

fun simulateFish(
    initialList: List<Lanternfish>,
    numberOfDays: Int
): List<Lanternfish> {
    val sequence = generateSequence(initialList) { currentListOfFish ->
        val numberOfFishThatWillReset = currentListOfFish.count { it.willReset() }

        currentListOfFish.map { it.age() } + List(numberOfFishThatWillReset) { Lanternfish.spawn() }
    }

    return sequence.elementAt(numberOfDays).toList()

}

@JvmInline
value class Lanternfish(
    private val counter: Int
) {
    fun age() = when (counter) {
        0 -> Lanternfish(6)
        else -> Lanternfish(counter - 1)
    }

    fun willReset() = counter == 0

    override fun toString(): String {
        return counter.toString()
    }

    companion object {
        fun spawn() = Lanternfish(8)
    }
}