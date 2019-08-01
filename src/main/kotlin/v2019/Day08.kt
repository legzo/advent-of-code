package gg.jte.aoc.v2019

import gg.jte.aoc.getTextFromFile

fun main() {
    val input = getTextFromFile("v2019/day_08.txt").trim()
    println(input.toLayerChecksum(25, 6))
    println(
        input
            .toLayers(25, 6)
            .mergeAll()
            .toStringRepresentation()
    )
}

fun String.toLayers(width: Int, height: Int) = this
    .chunked(width * height)
    .map { Layer(it, width, height) }

fun List<Layer>.findInterestingLayer() = minByOrNull { it.numberOf('0') }!!

data class Layer(val pixels: String, val width: Int, val height: Int) {

    val checksum: Int = numberOf('1') * numberOf('2')

    fun numberOf(char: Char) = pixels.count { it == char }

    fun mergeWith(other: Layer) =
        Layer(pixels
            .zip(other.pixels)
            .map { (currentLayerPixel, underLayerPixel) ->
                when {
                    currentLayerPixel.isTransparent() -> underLayerPixel
                    else -> currentLayerPixel
                }
            }
            .joinToString(separator = ""), width, height)

    private fun Char.isTransparent() = this == '2'

    fun toStringRepresentation(): String {
        return pixels
            .replace("0", " ")
            .chunked(width)
            .joinToString(separator = "\n") { it }
    }
}

fun List<Layer>.mergeAll(): Layer {
    return fold(this[0]) { topLayer, underLayer ->
        topLayer.mergeWith(underLayer)
    }
}

fun String.toLayerChecksum(width: Int, height: Int) =
    toLayers(width, height)
        .findInterestingLayer()
        .checksum
