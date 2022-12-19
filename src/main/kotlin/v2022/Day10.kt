package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2022/day10.txt")
    measureTimeAndPrint { parseAsCycles(input).sumOfSignalStrengths() }
}

fun parseAsCycles(input: String) =
    input
        .lines()
        .flatMap {
            val parts = it.split(" ")
            if (parts.size == 2) {
                listOf(NoopCycle, CycleWithEffect(parts[1].toInt()))
            } else listOf(NoopCycle)
        }

fun List<Int>.signalStrength(atCycle: Int): Int =
    this[atCycle - 1] * atCycle

fun List<Cycle>.sumOfSignalStrengths(): Int {
    val signalValues = signalValues()
    return (60..220 step 40).sumOf {
        signalValues.signalStrength(it)
    } + signalValues.signalStrength(20)
}

fun List<Cycle>.signalValues(): List<Int> =
    scan(initial = 1) { acc, cycle ->
        when (cycle) {
            NoopCycle -> acc
            is CycleWithEffect -> acc + cycle.effect
        }
    }

sealed interface Cycle

object NoopCycle : Cycle {
    override fun toString(): String = "NoopCycle"
}

data class CycleWithEffect(val effect: Int) : Cycle