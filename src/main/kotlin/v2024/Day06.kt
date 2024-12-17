package gg.jte.aoc.v2024

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.v2024.Direction.DOWN
import gg.jte.aoc.v2024.Direction.LEFT
import gg.jte.aoc.v2024.Direction.RIGHT
import gg.jte.aoc.v2024.Direction.UP
import gg.jte.aoc.v2024.ItemType.EMPTY
import gg.jte.aoc.v2024.ItemType.GUARD
import gg.jte.aoc.v2024.ItemType.OBSTACLE

fun main() {

    val input = getTextFromFile("v2024/day06.txt")

    val guardMap = parseAsMap(input)
    measureTimeAndPrint { getGuardPositions(guardMap).distinctPositions().size }
    measureTimeAndPrint {
        val pastPositions = getGuardPositions(guardMap).distinctPositions().drop(1)
        pastPositions.count {
            val newMap = guardMap.setObstacleAt(it)
            val last = getGuardPositions(newMap).last()
            last?.stepsCounter == 6999
        }
    }

}

fun parseAsMap(input: String) = GuardMap(
    items = input
        .lines()
        .flatMapIndexed { y, line ->
            line.mapIndexed { x, char ->
                Item(position = Position(x, y), type = char.toItemType(), char = char)
            }
        }
)

private fun Char.toItemType() = when (this) {
    '.' -> EMPTY
    '#' -> OBSTACLE
    else -> GUARD
}

data class GuardMap(
    val items: List<Item>,
    val dimension: Int = items.maxOf { it.position.x },
    val itemsByPosition: Map<Position, Item> = items.associateBy { it.position },
    val guard: Guard = with(items.first { it.type == GUARD }) {
        Guard(position = this.position, facing = this.char.toDirection())
    }
) {
    fun setObstacleAt(position: Position): GuardMap {
        val itemToRemove = items.first { it.position == position }
        val newItems = buildList {
            addAll(items)
            remove(itemToRemove)
            add(Item(position = position, type = OBSTACLE, char = '#'))
        }
        return GuardMap(items = newItems)
    }
}

fun getGuardPositions(guardMap: GuardMap): Sequence<Guard?> {
    val guard = guardMap.guard

    val guardSequence: Sequence<Guard?> = generateSequence(guard) { guard -> guard.move(guardMap) }

    return guardSequence
        .takeWhile { it != null && it.stepsCounter <= 6999 }
}

fun Sequence<Guard?>.distinctPositions() =
    mapNotNull { it?.position }
        .toList()
        .distinct()

data class Position(
    val x: Int,
    val y: Int,
)

data class Item(
    val position: Position,
    val type: ItemType,
    val char: Char,
)

enum class ItemType { EMPTY, OBSTACLE, GUARD }

data class Guard(
    val position: Position,
    val facing: Direction,
    val stepsCounter: Int = 0,
) {

    fun move(map: GuardMap): Guard? {
        val nextPosition = when (facing) {
            UP -> position.copy(y = position.y - 1)
            DOWN -> position.copy(y = position.y + 1)
            LEFT -> position.copy(x = position.x - 1)
            RIGHT -> position.copy(x = position.x + 1)
        }

        return when (map.itemsByPosition[nextPosition]?.type) {
            null -> null
            EMPTY, GUARD -> this.copy(position = nextPosition, stepsCounter = stepsCounter + 1)
            OBSTACLE -> this.copy(facing = facing.pivotRight())
        }
    }
}

fun Direction.pivotRight() =
    when (this) {
        UP -> RIGHT
        DOWN -> LEFT
        LEFT -> UP
        RIGHT -> DOWN
    }

enum class Direction { UP, DOWN, LEFT, RIGHT }

fun Char.toDirection() =
    when (this) {
        '^' -> UP
        '>' -> RIGHT
        '<' -> LEFT
        else -> DOWN
    }


