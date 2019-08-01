package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFileAsInts
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.shouldNotHappen

fun main() {

    val input = getLinesFromFileAsInts("v2020/day_01.txt")

    // Part 1
    measureTimeAndPrint { with(input.findPairWithSum(2020)) { first * second } }

    // Part 2
    measureTimeAndPrint { with(input.findTripleWithSum(2020)) { first * second * third } }

}

fun List<Int>.findPairWithSum(total: Int): Pair<Int, Int> {
    val complementList = map { total - it }
    val first = find { it in complementList } ?: shouldNotHappen()
    return first to (total - first)
}

fun List<Int>.findTripleWithSum(total: Int) =
    findTripleMatching { it.first + it.second + it.third == total }
        ?: shouldNotHappen()

fun List<Int>.findTripleMatching(predicate: (Triple<Int, Int, Int>) -> Boolean): Triple<Int, Int, Int>? {
    forEach { first ->
        forEach { second ->
            forEach { third ->
                if (predicate(Triple(first, second, third))) return Triple(first, second, third)
            }
        }
    }
    return null
}