package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import gg.jte.aoc.v2020.ExecutionState.Companion.start
import gg.jte.aoc.v2020.Instruction.Accumulate
import gg.jte.aoc.v2020.Instruction.Jump
import gg.jte.aoc.v2020.Instruction.Noop
import gg.jte.aoc.v2020.Termination.CorrectTermination
import gg.jte.aoc.v2020.Termination.InfiniteLoop

fun main() {

    val lines = getLinesFromFile("v2020/day_08.txt")
    val instructions = lines.parseAsInstructions()

    // Part 1
    measureTimeAndPrint { instructions.execute().lastAccumulatorValue }

    // Part 2
    measureTimeAndPrint { instructions.fixAndExecute().lastAccumulatorValue }

}

sealed class Instruction {

    abstract fun applyTo(currentState: ExecutionState): ExecutionState

    data class Jump(val offset: Int) : Instruction() {
        override fun applyTo(currentState: ExecutionState) = with(currentState) {
            copy(
                currentIndex = currentIndex + offset,
                alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
            )
        }
    }

    data class Accumulate(val value: Int) : Instruction() {
        override fun applyTo(currentState: ExecutionState) = with(currentState) {
            copy(
                currentIndex = currentIndex + 1,
                accumulatorValue = accumulatorValue + value,
                alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
            )
        }
    }

    // Noop should really be an object, but value is used in part 2 for mutating to Jump
    data class Noop(val value: Int) : Instruction() {
        override fun applyTo(currentState: ExecutionState) = with(currentState) {
            copy(
                currentIndex = currentIndex + 1,
                alreadyVisitedInstructions = alreadyVisitedInstructions + currentIndex
            )
        }
    }
}

sealed class Termination {
    abstract val lastAccumulatorValue: Int

    data class InfiniteLoop(override val lastAccumulatorValue: Int) : Termination()
    data class CorrectTermination(override val lastAccumulatorValue: Int) : Termination()
}

data class ExecutionState(
    val currentIndex: Int,
    val accumulatorValue: Int,
    val alreadyVisitedInstructions: Set<Int>
) {
    companion object {
        val start =
            ExecutionState(
                currentIndex = 0,
                accumulatorValue = 0,
                alreadyVisitedInstructions = setOf()
            )
    }
}

fun List<Instruction>.fixAndExecute(): Termination {

    val allMutations: List<List<Instruction>> =
        this.mapIndexedNotNull { index, instruction ->
            when (instruction) {
                is Jump -> this.copyAndReplaceAtIndexWith(index, Noop(instruction.offset))
                is Noop -> this.copyAndReplaceAtIndexWith(index, Jump(instruction.value))
                is Accumulate -> null // output is not mutated so we skip this variation
            }
        }

    return allMutations
        .asSequence()
        .map { it.execute() }
        .first { it is CorrectTermination }
}

fun List<Instruction>.execute(currentState: ExecutionState = start): Termination {

    val currentExecutionIndex = currentState.currentIndex

    if (currentExecutionIndex in currentState.alreadyVisitedInstructions)
        return InfiniteLoop(currentState.accumulatorValue)

    if (currentExecutionIndex == this.size)
        return CorrectTermination(currentState.accumulatorValue)

    val currentInstruction = this[currentExecutionIndex]

    val nextState = currentInstruction.applyTo(currentState)

    return execute(nextState)
}

fun List<String>.parseAsInstructions(): List<Instruction> =
    mapNotNull { line ->
        line.parseWithRegex("([\\w]+) ([+-][\\d]+)") { (actionAsString, valueAsString) ->
            val value = valueAsString.toInt()
            when (actionAsString) {
                "nop" -> Noop(value)
                "jmp" -> Jump(value)
                "acc" -> Accumulate(value)
                else -> null
            }
        }
    }

private fun List<Instruction>.copyAndReplaceAtIndexWith(index: Int, newInstruction: Instruction) =
    this.toMutableList()
        .apply { this[index] = newInstruction }
        .toList()
