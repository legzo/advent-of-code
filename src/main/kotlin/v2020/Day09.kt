package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFileAsLongs
import gg.jte.aoc.measureTimeAndPrint

fun main() {

    val numbers = getLinesFromFileAsLongs("v2020/day_09.txt")

    // Part 1
    measureTimeAndPrint { numbers.findInvalidElement(chunkSize = 25) }

    // Part 2
    measureTimeAndPrint {
        numbers
            .findContiguousRangeProducingInvalidNumber(chunkSize = 25)
            .sumExtrema()
    }

}

data class LookupForSum(
    val totalToFind: Long,
    val selectableItems: List<Long>
) {
    fun isValid(): Boolean {
        val complements = selectableItems.map { totalToFind - it }
        return selectableItems.any { it in complements }
    }
}

fun List<Long>.findInvalidElement(chunkSize: Int): Long {
    val lookups: List<LookupForSum> =
        this.drop(chunkSize)
            .mapIndexed { index, currentLong ->
                LookupForSum(
                    currentLong,
                    this.subList(index, index + chunkSize)
                )
            }

    return lookups
        .first { !it.isValid() }
        .totalToFind
}

data class AccumulationState(
    val range: List<Long>,
    val total: Long
) {
    companion object {
        val start = AccumulationState(listOf(), 0L)
    }
}

fun List<Long>.findContiguousRangeProducingInvalidNumber(chunkSize: Int): List<Long> {
    val invalidElement = findInvalidElement(chunkSize)

    return mapIndexedNotNull { index: Int, _: Long ->
        this.drop(index)
            .fold(AccumulationState.start) { currentState, currentValue ->
                val newTotal = currentState.total + currentValue
                val newState = AccumulationState(
                    range = currentState.range + currentValue,
                    total = newTotal
                )
                when {
                    newTotal == invalidElement -> return@mapIndexedNotNull newState
                    newTotal > invalidElement -> return@mapIndexedNotNull null
                    else -> newState
                }
            }
    }.first().range

}

fun List<Long>.sumExtrema(): Long {
    return minOf { it } + maxOf { it }
}
