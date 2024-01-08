package gg.jte.aoc.v2023

import gg.jte.aoc.findLCM
import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import gg.jte.aoc.v2023.Instruction.LEFT
import gg.jte.aoc.v2023.Instruction.RIGHT

fun main() {
    val input = getTextFromFile("v2023/day08.txt")
    measureTimeAndPrint { input.parseAsDocument().countIterationsForHumans() }
    measureTimeAndPrint { input.parseAsDocument().countIterationsForGhosts() }
}

data class Document(
    val instructions: List<Instruction>,
    val nodes: Map<String, Node>,
) {

    val startingNodes =
        nodes
            .filter { it.key.endsWith("A") }
            .values

    private val instructionSequence: Sequence<Pair<Long, Instruction>> =
        generateSequence((1L to instructions.first())) { (index, _) ->
            val length = instructions.size
            (index + 1) to instructions[(index % length).toInt()]
        }

    private fun followInstructions(startingNode: Node?, whileCondition: (Node) -> Boolean): Sequence<Node> =
        instructionSequence
            .scan(startingNode) { node, (_, instruction) ->
                if (node == null) error("Node can't be null though, can it ?!")

                when (instruction) {
                    LEFT -> nodes[node.left]
                    RIGHT -> nodes[node.right]
                }
            }
            .filterNotNull()
            .takeWhile(whileCondition)

    fun countIterationsForHumans(): Long =
        followInstructions(startingNode = nodes["AAA"], whileCondition = { node -> node.id != "ZZZ" })
            .toList()
            .size
            .toLong()

    fun countIterationsForGhosts(): Long =
        startingNodes
            .map {
                followInstructions(
                    startingNode = it,
                    whileCondition = { node -> !node.id.endsWith("Z") }
                ).toList().size.toLong()
            }
            .reduce { a, b -> findLCM(a, b) }

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
            it.parseWithRegex("""(\w{3}) = \((\w{3}), (\w{3})\)""") { (id, left, right) ->
                Node(id, left, right)
            }
        }.associateBy { it.id },
    )

fun Char.asInstruction() = when (this) {
    'L' -> LEFT
    'R' -> RIGHT
    else -> error("wut ?")
}