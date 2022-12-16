package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.v2022.Direction.*

fun main() {
    val input = getTextFromFile("v2022/day08.txt")
    measureTimeAndPrint { MapOfTrees(input).countVisibleTrees() }
}

class MapOfTrees(asString: String) {
    val height = asString.lines().size
    val width = asString.lines().first().length

    private val trees = asString.lines().map { row ->
        row.map(Char::digitToInt).toIntArray()
    }.toTypedArray()

    fun treeHeightAt(x: Int, y: Int): Int = trees[y][x]

    fun trees(direction: Direction, of: Tree): List<Int> =
        when (direction) {
            UP -> verticalTrees(fromY = 0, untilY = of.y, atX = of.x)
            DOWN -> verticalTrees(fromY = of.y + 1, untilY = height, atX = of.x)
            LEFT -> horizontalTrees(0, of.x, of.y)
            RIGHT -> horizontalTrees(of.x + 1, width, of.y)
        }

    private fun horizontalTrees(fromX: Int, untilX: Int, atY: Int) =
        (fromX until untilX).map { treeHeightAt(it, atY) }

    private fun verticalTrees(fromY: Int, untilY: Int, atX: Int) =
        (fromY until untilY).map { treeHeightAt(atX, it) }

    fun countVisibleTrees(): Int =
        (0 until width).sumOf { x ->
            (0 until height).count { y ->
                Tree(x = x, y = y).isVisible()
            }
        }

}

context(MapOfTrees)
fun Tree.isVisible(): Boolean {
    val treeHeight = treeHeightAt(x = x, y = y)
    return Direction.values().any { direction ->
        trees(direction, of = this).all { it < treeHeight }
    }
}

data class Tree(
    val x: Int,
    val y: Int,
)

enum class Direction { UP, DOWN, LEFT, RIGHT }