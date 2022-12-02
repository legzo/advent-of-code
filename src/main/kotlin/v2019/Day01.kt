package gg.jte.aoc.v2019

import gg.jte.aoc.getLinesFromFileAsInts


fun main() {
    val modules = getLinesFromFileAsInts("v2019/day_01.txt").toModules()
    println(Spacecraft(modules).calculateFuelNeeded())
    println(Spacecraft(modules).calculateTotalFuelNeeded())
}

private fun List<Int>.toModules(): List<Module> =
    map { Module(it) }

data class Module(
    val mass: Int
) {

    fun calculateFuelNeeded(): Int = calculateFuelNeededForGivenMass(this.mass)

    fun calculateTotalFuelNeeded(): Int = calculateFuelNeededRec(this.mass)

}

fun calculateFuelNeededForGivenMass(mass: Int) = mass / 3 - 2

fun calculateFuelNeededRec(mass: Int): Int {
    val fuelNeeded = calculateFuelNeededForGivenMass(mass)
    return if (fuelNeeded > 0) fuelNeeded + calculateFuelNeededRec(fuelNeeded)
    else 0
}

data class Spacecraft(
    val modules: List<Module>
) {
    fun calculateFuelNeeded(): Int = modules.sumOf { it.calculateFuelNeeded() }
    fun calculateTotalFuelNeeded(): Int = modules.sumOf { it.calculateTotalFuelNeeded() }
}
