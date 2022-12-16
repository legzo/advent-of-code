package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.v2022.Direction.*

fun main() {
    val input = getTextFromFile("v2022/day08.txt")
    measureTimeAndPrint { MapOfTrees(input).countVisibleTrees() }
}

class MapOfTrees(private val asString: String) {
    val height = asString.lines().size
    val width = asString.lines().first().length

    fun treeAt(x: Int, y: Int): Int = asString.lines()[y][x].toString().toInt()

    fun trees(direction: Direction, of: Tree): List<Int> =
        when (direction) {
            UP -> verticalTrees(fromY = 0, untilY = of.y, atX = of.x)
            DOWN -> verticalTrees(fromY = of.y + 1, untilY = height, atX = of.x)
            LEFT -> horizontalTrees(0, of.x, of.y)
            RIGHT -> horizontalTrees(of.x + 1, width, of.y)
        }

    private fun horizontalTrees(fromX: Int, untilX: Int, atY: Int) =
        (fromX until untilX).map { treeAt(it, atY) }

    private fun verticalTrees(fromY: Int, untilY: Int, atX: Int) =
        (fromY until untilY).map { treeAt(atX, it) }

    fun countVisibleTrees(): Int =
        (0 until width).sumOf { x ->
            (0 until height).count { y ->
                println("Checkin $x:$y")
                Tree(x = x, y = y).isVisible()
            }
        }

}

context(MapOfTrees)
fun Tree.isVisible(): Boolean {
    val height = treeAt(x = x, y = y)
    return Direction.values().any { direction ->
        trees(direction, of = this).all { it < height }
    }
}

data class Tree(
    val x: Int,
    val y: Int,
)

enum class Direction { UP, DOWN, LEFT, RIGHT }