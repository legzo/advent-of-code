package gg.jte.aoc.v2018

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.v2018.Polymer.Status.FoundMatch
import gg.jte.aoc.v2018.Polymer.Status.LookingForMatch
import gg.jte.aoc.v2018.Polymer.Status.RemovedMatch
import java.time.LocalDateTime

fun main() {

    println(
        Polymer(getLinesFromFile("v2018/day_05.txt")[0])
            .reactFully().size
    ) // 10878

    println(
        Polymer(getLinesFromFile("v2018/day_05.txt")[0])
            .findShortestPolymerLength()
    ) // 71976
}

infix fun Char.reactsWith(other: Char): Boolean {
    return this.lowercaseChar() == other.lowercaseChar()
            && this != other
}

class Polymer(val stringRepresentation: String) {

    val size
        get() = stringRepresentation.length

    fun reactFully(): Polymer {
        findReactingPart() ?: return this
        return this.react().reactFully()
    }

    fun react(): Polymer {
        val reactingPart = this.findReactingPart()
        return if (reactingPart != null) Polymer(this.stringRepresentation.replaceFirst(reactingPart, ""))
        else this
    }

    private fun findReactingPart(): String? {
        stringRepresentation.zipWithNext { current, next ->
            if (current reactsWith next) return listOf(current, next).joinToString(separator = "")
        }

        return null
    }

    fun findShortestPolymerLength() =
        ('A'..'Z').minOfOrNull { this.copyWithoutIgnoreCase(it).reactFully().size }

    private fun copyWithoutIgnoreCase(char: Char) = Polymer(
        stringRepresentation
            .replace(char.uppercaseChar().toString(), "")
            .replace(char.lowercaseChar().toString(), "")
    )

    //<editor-fold desc="old">
    fun oldReact(): Polymer {
        val accStart = Accumulator(LookingForMatch, '*', listOf())

        val (_, _, chars) = stringRepresentation
            .plus('*')
            .fold(accStart) { acc, char ->
                when (acc.status) {
                    is LookingForMatch -> {
                        if (acc.previousChar reactsWith char) {
                            Accumulator(FoundMatch, char, acc.chars)
                        } else Accumulator(LookingForMatch, char, acc.chars + acc.previousChar)

                    }
                    is FoundMatch -> Accumulator(RemovedMatch, char, acc.chars)
                    is RemovedMatch -> Accumulator(RemovedMatch, char, acc.chars + acc.previousChar)
                }
            }

        return Polymer(chars.joinToString(separator = "").drop(1))
    }

    data class Accumulator(val status: Status, val previousChar: Char, val chars: List<Char>)

    sealed class Status {
        object FoundMatch : Status()
        object RemovedMatch : Status()
        object LookingForMatch : Status()
    }
    //</editor-fold>

}
