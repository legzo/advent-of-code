package gg.jte.aoc.v2019

fun String.isAValidPassword(): Boolean {
    val listOfInts = toListOfInts()
    return listOfInts.containsDouble() && listOfInts.neverDecreases()
}

fun List<Int>.containsDouble(): Boolean {
    return zipWithNext()
        .any { (current, next) -> current == next }
}

fun List<Int>.neverDecreases(): Boolean {
    return zipWithNext()
        .all { (current, next) -> current <= next }
}

fun String.toListOfInts() = toCharArray().map { it.code }

fun String.countValidPasswordsInRange(): Int {
    val rangeLimits = split("-")
    val start = rangeLimits[0].toInt()
    val end = rangeLimits[1].toInt()

    return (start..end)
        .count { it.toString().isAValidPassword() }

}
