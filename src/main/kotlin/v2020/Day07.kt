package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex

fun main() {

    val lines = getLinesFromFile("v2020/day_07.txt")

    val bagConstraints = lines
        .mapNotNull { line -> parseAsBagConstraint(line) }

    // Part 1
    measureTimeAndPrint { bagConstraints.findAllContainersFor(Bag("shiny gold")).count() }

    // Part 2
    measureTimeAndPrint { bagConstraints.countAllPossibleContainedBagsFor(Bag("shiny gold")) }
}

data class Bag(val color: String)

data class BagConstraint(
    val container: Bag,
    val contents: List<Content>
) {
    val bagContents = contents.map { it.bag }
}

data class Content(
    val quantity: Int,
    val bag: Bag
)

fun parseAsBagConstraint(line: String): BagConstraint? {

    fun String.parseContent() =
        parseWithRegex("([\\d]+) ([\\w]+ [\\w]+) bags?") { (quantity, bagColor) ->
            Content(quantity = quantity.toInt(), bag = Bag(bagColor))
        }

    return line.parseWithRegex("([\\w]+ [\\w]+) bags contain (.*)") { (containerColor, contentsAsString) ->
        val contents = contentsAsString
            .split(',', '.')
            .mapNotNull { contentAsString -> contentAsString.trim().parseContent() }

        BagConstraint(container = Bag(containerColor), contents = contents)
    }
}

fun List<BagConstraint>.findAllContainersFor(bag: Bag): Set<Bag> {
    val directContainers = findDirectContainersFor(bag)
    return directContainers + directContainers.flatMap { findAllContainersFor(it) }
}

fun List<BagConstraint>.countAllPossibleContainedBagsFor(bag: Bag): Int {
    return countDirectContainedBags(bag) +
            findContentsFor(bag)
                .sumOf { it.quantity * countAllPossibleContainedBagsFor(it.bag) }
}

private fun List<BagConstraint>.findContentsFor(bag: Bag): List<Content> =
    first { it.container == bag }
        .contents

private fun List<BagConstraint>.findDirectContainersFor(bag: Bag): Set<Bag> =
    filter { bag in it.bagContents }
        .map { it.container }
        .toSet()

private fun List<BagConstraint>.countDirectContainedBags(bag: Bag): Int =
    findContentsFor(bag)
        .sumOf { content -> content.quantity }
