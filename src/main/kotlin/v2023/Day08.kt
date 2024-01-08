package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import gg.jte.aoc.v2023.Instruction.LEFT
import gg.jte.aoc.v2023.Instruction.RIGHT

fun main() {
    val input = getTextFromFile("v2023/day08.txt")
    measureTimeAndPrint { input.parseAsDocument().followInstructions().toList().size }
}

data class Document(
    val instructions: List<Instruction>,
    val nodes: Map<String, Node>,
) {

    private val instructionSequence: Sequence<Pair<Long, Instruction>> =
        generateSequence((1L to instructions.first())) { (index, _) ->
            val length = instructions.size
            (index + 1) to instructions[(index % length).toInt()]
        }

    fun followInstructions(): Sequence<Node> =
        instructionSequence
            .scan(nodes["AAA"]) { node, (_, instruction) ->
                if (node == null) error("Node can't be null though, can it ?!")

                when (instruction) {
                    LEFT -> nodes[node.left]
                    RIGHT -> nodes[node.right]
                }
            }
            .filterNotNull()
            .takeWhile { it.id != "ZZZ" }
}

enum class Instruction { LEFT, RIGHT }

data class Node(
    val id: String,
    val left: String,
    val right: String,
)

fun String.parseAsDocument(): Document = lines().parseAsDocument()

fun List<String>.parseAsDocument() =
    Document(
        instructions = first().map { it.asInstruction() },
        nodes = drop(1).mapNotNull {
            it.parseWithRegex("""([A-Z]{3}) = \(([A-Z]{3}), ([A-Z]{3})\)""") { (id, left, right) ->
                Node(id, left, right)
            }
        }.associateBy { it.id },
    )

fun Char.asInstruction() = when (this) {
    'L' -> LEFT
    'R' -> RIGHT
    else -> error("wut ?")
}