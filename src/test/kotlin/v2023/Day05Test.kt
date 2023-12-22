package gg.jte.aoc.v2023

import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.math.min

internal class Day05Test {

    private val input = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
        """.trimIndent()

    @Test
    fun `should parse almanac`() {
        val almanac = input.parseAsAlmanac(::parseIndividualSeeds)
        almanac.seeds shouldBe listOf(79, 14, 55, 13)
        almanac.maps shouldHaveSize 7
        almanac.maps.first().ranges shouldHaveSize 2
        almanac.maps[2].ranges shouldHaveSize 4
    }

    @Test
    fun `should get corresponding range output`() {
        val range = AlmanacRange(
            sourceRangeStart = 98,
            destinationRangeStart = 50,
            length = 2,
        )

        range.getCorrespondingOutput(98) shouldBe 50
        range.getCorrespondingOutput(99) shouldBe 51

        val range2 = AlmanacRange(
            sourceRangeStart = 50,
            destinationRangeStart = 52,
            length = 48,
        )
        range2.getCorrespondingOutput(50) shouldBe 52
        range2.getCorrespondingOutput(54) shouldBe 56
    }

    @Test
    fun `should get corresponding range output for map`() {
        val map = AlmanacMap(
            ranges = listOf(
                AlmanacRange(
                    sourceRangeStart = 98,
                    destinationRangeStart = 50,
                    length = 2,
                ),
                AlmanacRange(
                    sourceRangeStart = 50,
                    destinationRangeStart = 52,
                    length = 48,
                )
            )
        )

        map.getCorrespondingOutput(0) shouldBe 0
        map.getCorrespondingOutput(1) shouldBe 1
        map.getCorrespondingOutput(2) shouldBe 2
        map.getCorrespondingOutput(49) shouldBe 49
        map.getCorrespondingOutput(50) shouldBe 52
        map.getCorrespondingOutput(51) shouldBe 53
        map.getCorrespondingOutput(96) shouldBe 98
        map.getCorrespondingOutput(97) shouldBe 99
        map.getCorrespondingOutput(98) shouldBe 50
        map.getCorrespondingOutput(99) shouldBe 51
    }

    @Test
    fun `should map seeds all the way`() {
        input.parseAsAlmanac(::parseIndividualSeeds).getFinalSeedsLocations() shouldBe listOf(82, 43, 86, 35)
    }

    @Test
    fun `should parse seeds as ranges`() {
        parseSeedsAsRanges("79 14 55 13") shouldBe listOf(79L..92L, 55L..67L)
    }

    @Test
    fun `should make subrange`() {
        (10L..20L) inter (5L..25L) shouldBe (10L..20L)
        (10L..20L) inter (5L..20L) shouldBe (10L..20L)
        (10L..20L) inter (5L..15L) shouldBe (10L..15L)
        (10L..20L) inter (15L..20L) shouldBe (15L..20L)
        (10L..20L) inter (15L..25L) shouldBe (15L..20L)
        (10L..20L) inter (10L..20L) shouldBe (10L..20L)
        (10L..20L) inter (0L..5L) shouldBe null
    }

    private infix fun LongRange.inter(range2: LongRange): LongRange? {
        return if (intersect(range2).isEmpty()) null
        else max(first, range2.first)..min(last, range2.last)
    }

    @Test
    fun `should translate range`() {
        val almanacRange = AlmanacRange(
            sourceRangeStart = 5,
            destinationRangeStart = 10,
            length = 10
        )
        almanacRange.translate(5L..25L) shouldBe (10L..20L)

        almanacRange.translate(5L..10L) shouldBe (10L..15L)

        almanacRange.translate(0L..4L) shouldBe null

        almanacRange.translate(20L..25L) shouldBe null

        almanacRange.translate(0L..25L) shouldBe (10L..20L)
    }

    @Test
    fun `should fill map`() {
        val map = AlmanacMap(
            ranges = listOf(
                AlmanacRange(
                    sourceRangeStart = 50,
                    destinationRangeStart = 52,
                    length = 40,
                ),
                AlmanacRange(
                    sourceRangeStart = 95,
                    destinationRangeStart = 50,
                    length = 2,
                )
            )
        )

        val seedsRanges = listOf((40L..70L), (79L..90L))

        map.fillFor(seedsRanges).ranges shouldContainAll listOf(
            AlmanacRange.build(start = 40, end = 50, offset = 0),
            AlmanacRange.build(start = 50, end = 90, offset = 2),
            AlmanacRange.build(start = 90, end = 95, offset = 0),
            AlmanacRange.build(start = 95, end = 97, offset = -45),
        )
    }

    @Test
    fun `should process seeds ranges`() {
        processRanges(input)
            .sortedBy { it.first }
            .minOf { it.first } shouldBe 46
    }
}