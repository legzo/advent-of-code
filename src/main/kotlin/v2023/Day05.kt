package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = getTextFromFile("v2023/day05.txt")
    measureTimeAndPrint { input.parseAsAlmanac(::parseIndividualSeeds).getFinalSeedsLocations().min() }
    measureTimeAndPrint { processRanges(input)
        .sortedBy { it.first }
        .minOf { it.first } }
}

data class Almanac(
    val seeds: List<Long>,
    val maps: List<AlmanacMap>
) {
    fun getFinalSeedsLocations(): List<Long> =
        maps
            .fold(seeds) { currentSeeds, layerMap ->
                currentSeeds.map { layerMap.getCorrespondingOutput(it) }
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
    val first = sourceRangeStart
    val last = sourceRangeStart + length
    val offset = destinationRangeStart - sourceRangeStart

    fun contains(value: Long): Boolean =
        value in (sourceRangeStart..sourceRangeStart + length)

    fun getCorrespondingOutput(input: Long): Long =
        input + (destinationRangeStart - sourceRangeStart)

    override fun toString(): String = "[$first->$last] $offset"

    companion object {
        fun build(start: Long, end: Long, offset: Long): AlmanacRange =
            AlmanacRange(
                sourceRangeStart = start,
                destinationRangeStart = start + offset,
                length = end - start
            )
    }

}

fun String.parseAsAlmanac(seedsParsingFunction: (String) -> List<Long>): Almanac {
    val groups = split("\n\n")
    val seedsLine = groups.first()
    val mapsGroups = groups.drop(1)

    return Almanac(
        seeds = seedsParsingFunction(seedsLine),
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

private fun String.parseAsAlmanachMaps() = parseAsAlmanac { listOf() }.maps

internal fun parseIndividualSeeds(seedsLine: String) = seedsLine
    .split(" ")
    .mapNotNull { it.toLongOrNull() }

internal fun parseSeedsAsRanges(seedsLine: String) = seedsLine
    .split(" ")
    .mapNotNull { it.toLongOrNull() }
    .windowed(size = 2, step = 2)
    .map { (it[0]..<it[0] + it[1]) }

internal fun AlmanacMap.fillFor(seeds: List<LongRange>): AlmanacMap {
    val seedsMin = seeds.minOf { it.first }
    val seedsMax = seeds.maxOf { it.last }

    val rangesMin = ranges.minOf { it.first }
    val rangesMax = ranges.maxOf { it.last }

    return this.copy(
        ranges = buildList {

            addAll(ranges)

            if (seedsMin < rangesMin)
                add(AlmanacRange.build(start = seedsMin, end = rangesMin, offset = 0))

            if (rangesMax < seedsMax)
                add(AlmanacRange.build(start = rangesMax, end = seedsMax, offset = 0))

            ranges
                .sortedBy { it.first }
                .zipWithNext { current, next ->
                if (next.first > current.last) // there's a gap
                    add(AlmanacRange.build(start = current.last, end = next.first, offset = 0))
            }
        })
}

internal fun AlmanacRange.translate(range: LongRange): LongRange? {
    return if (!(first <= range.last && last >= range.first)) null
    else max(range.first, first) + offset..min(range.last, last) + offset
}

internal fun processRanges(input: String): List<LongRange> {
    val seedsRanges: List<LongRange> = parseSeedsAsRanges(input.lines().first())
    val almanacMaps: List<AlmanacMap> = input.parseAsAlmanachMaps()

    return almanacMaps.foldIndexed(seedsRanges) { index: Int, ranges: List<LongRange>, layerMap: AlmanacMap ->
        println("Layer $index, ${ranges.size} ranges")
        val filledRangesForLayer = layerMap
            .fillFor(ranges)
            .ranges

        ranges.flatMap { seedsRange -> filledRangesForLayer.mapNotNull { it.translate(seedsRange) } }
            .distinct()
    }

}