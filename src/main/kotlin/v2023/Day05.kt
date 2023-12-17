package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2023/day05.txt")
    measureTimeAndPrint {
        input.parseAsAlmanac().getFinalSeedsLocations().min()
    }
}

data class Almanac(
    val seeds: List<Long>,
    val maps: List<AlmanacMap>
) {
    fun getFinalSeedsLocations(): List<Long> =
        maps
            .fold(seeds) { acc, almanacMap ->
                acc.map { almanacMap.getCorrespondingOutput(it) }
            }
}

data class AlmanacMap(
    val ranges: List<AlmanacRange>
) {
    fun getCorrespondingOutput(input: Long): Long =
        ranges.firstOrNull { it.contains(input) }
            ?.getCorrespondingOutput(input)
            ?: input
}

data class AlmanacRange(
    val sourceRangeStart: Long,
    val destinationRangeStart: Long,
    val length: Long,
) {
    fun contains(value: Long): Boolean =
        value in (sourceRangeStart..sourceRangeStart + length)

    fun getCorrespondingOutput(input: Long): Long =
        input + (destinationRangeStart - sourceRangeStart)
}

fun String.parseAsAlmanac(): Almanac {
    val groups = split("\n\n")
    val seedsLine = groups.first()
    val mapsGroups = groups.drop(1)

    return Almanac(
        seeds = seedsLine
            .split(" ")
            .mapNotNull { it.toLongOrNull() },
        maps = mapsGroups
            .map { mapGroup ->
                val rangesAsLines = mapGroup.lines().drop(1)
                AlmanacMap(ranges = rangesAsLines.map { rangeAsLine ->
                    val (destinationRangeStart, sourceRangeStart, length) = rangeAsLine.split(" ")
                    AlmanacRange(
                        sourceRangeStart = sourceRangeStart.toLong(),
                        destinationRangeStart = destinationRangeStart.toLong(),
                        length = length.toLong(),
                    )
                })
            }

    )
}