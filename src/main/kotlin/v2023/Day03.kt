package gg.jte.aoc.v2023

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import java.io.File

fun main() {
    val input = getTextFromFile("v2023/day03.txt")
    measureTimeAndPrint {
        input.parse()
            .partNumbers
            .sumOf { it.value.toInt() }
    }

    measureTimeAndPrint {
        input.parse()
            .gears
            .values
            .sum()
    }
}


data class Number(val point: Point, val value: String) {
    val length: Int = value.length
}

data class Symbol(val point: Point, val value: String)
data class Point(val x: Int, val y: Int)
data class ParsingContext(
    val symbols: List<Symbol> = listOf(),
    val numbers: List<Number> = listOf(),
    val currentNumber: Number? = null,
) {
    fun updateCurrentNumber(
        x: Int,
        y: Int,
        char: String
    ) = copy(
        currentNumber =
        if (currentNumber == null) Number(point = Point(x = x, y = y), value = char)
        else this.currentNumber.copy(value = this.currentNumber.value + char)
    )

    fun storeSymbol(x: Int, y: Int, value: String) =
        copy(symbols = symbols + Symbol(Point(x = x, y = y), value = value))

    fun storeCurrentNumber() =
        if (currentNumber != null) {
            copy(
                numbers = numbers + currentNumber,
                currentNumber = null
            )
        } else this

    val partNumbers: List<Number>
        get() = numbers.filter(::hasASymbolInNeighbourhood)

    val gears
        get() = numbers.flatMap(::getSymbolsInTheNeighbourhood)
            .groupBy { (symbol, _) -> symbol }
            .filter { it.value.size == 2 }
            .mapValues { (_, match) ->
                match
                    .map { (_, number) -> number.value.toInt() }
                    .reduce { a, b -> a * b }
            }

    private fun hasASymbolInNeighbourhood(number: Number): Boolean {
        val symbolPoints = symbols.map { it.point }
        val point = number.point

        return (point.x - 1..(point.x + number.length))
            .any { x ->
                (point.y - 1..(point.y + 1)).any { y ->
                    Point(x = x, y = y) in symbolPoints
                }
            }
    }

    private fun getSymbolsInTheNeighbourhood(number: Number): List<Pair<Symbol, Number>> =
        (number.point.x - 1..(number.point.x + number.length))
            .flatMap { x ->
                (number.point.y - 1..(number.point.y + 1)).mapNotNull { y ->
                    symbols.firstOrNull { it.point.x == x && it.point.y == y }
                        ?.let { it to number }
                }
            }
}

fun String.parse(): ParsingContext {
    val lines = lines()
    val width = lines[0].length

    return lines
        .joinToString(separator = "")
        .foldIndexed(ParsingContext()) { column, currentContext, char ->
            val x: Int = column % width
            val y: Int = column / width

            val context = if (x == 0) currentContext.storeCurrentNumber() else currentContext

            when {
                char.isDigit() -> context
                    .updateCurrentNumber(x = x, y = y, char = char.toString())

                char != '.' -> {
                    context
                        .storeSymbol(x = x, y = y, value = char.toString())
                        .storeCurrentNumber()
                }

                else -> context.storeCurrentNumber()
            }
        }
        .storeCurrentNumber()
}


//<editor-fold desc="Visu">
private fun ParsingContext.dump(filePath: String) {
    val spreadNumbers = numbers.spread()
    val spreadPartNumbers = partNumbers.spread()
    val s = ((0..139).joinToString(separator = "\n") { y ->
        (0..139).joinToString(separator = "") { x ->
            val symbol = symbols.firstOrNull { it.point.x == x && it.point.y == y }
            val partNumber = spreadPartNumbers.firstOrNull { it.point.x == x && it.point.y == y }
            val number = spreadNumbers.firstOrNull { it.point.x == x && it.point.y == y }
            symbol?.value
                ?: if (number != null && partNumber == null) "o" else number?.value
                    ?: "."
        }
    })

    File(filePath).writeText(s)
}

private fun List<Number>.spread() =
    flatMap { number ->
        number.value
            .mapIndexed { index, char ->
                Number(point = number.point.copy(x = number.point.x + index), value = char.toString())
            }
    }
//</editor-fold>
