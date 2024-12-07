package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2024/day03.txt")
    measureTimeAndPrint { input.extractMulInstructions().filterIsInstance<MulInstruction>().execute() }
    measureTimeAndPrint { input.extractMulInstructions().executeOnlyEnabled().value }
}

sealed interface Instruction

data object Do : Instruction
data object Dont : Instruction

data class MulInstruction(
    val first: Int,
    val second: Int,
) : Instruction {
    val result = first * second
}

val buildMulInstruction: (MatchResult.Destructured) -> MulInstruction =
    { (first: String, second: String) -> MulInstruction(first.toInt(), second.toInt()) }

fun List<MulInstruction>.execute() =
    sumOf { it.result }

fun String.extractMulInstructions(): List<Instruction> =
    Regex("""mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""")
        .findAll(this)
        .map {
            when {
                it.value.startsWith("mul") -> buildMulInstruction(it.destructured)
                it.value.startsWith("don't") -> Dont
                else -> Do
            }
        }
        .toList()

data class Context(
    val value: Int = 0,
    val enabled: Boolean = true,
) {
    fun enable() = copy(enabled = true)
    fun disable() = copy(enabled = false)
    fun execute(instruction: MulInstruction) = copy(value = value + instruction.result)
}

fun List<Instruction>.executeOnlyEnabled() =
    fold(Context()) { context, instruction ->
        when (instruction) {
            Do -> context.enable()
            Dont -> context.disable()
            is MulInstruction -> if (context.enabled) context.execute(instruction) else context
        }
    }