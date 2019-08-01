package gg.jte.aoc.v2018

import gg.jte.aoc.getLinesFromFile

fun main() {
    val particles = Particle.loadList(getLinesFromFile("v2018/day_10.txt"))

    val (finalParticles, steps) = particles.getStateWithMessage()

    println(finalParticles.draw()) // GFNKCGGH
    println(steps) // 10274
}

data class XYPosition(val x: Int, val y: Int)
data class Velocity(val x: Int, val y: Int)

data class Bounds(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int)
data class ParticlesState(val particles: List<Particle>, val steps: Int)

data class Particle(val position: XYPosition, val velocity: Velocity) {

    fun move() = copy(position = XYPosition(position.x + velocity.x, position.y + velocity.y))

    companion object {
        fun loadList(lines: List<String>): List<Particle> {
            val regexForDependency = Regex("position=<([^,]*),([^>]*)> velocity=<([^,]*),([^>]*)>")

            return lines
                .mapNotNull { line -> regexForDependency.find(line) }
                .map {
                    val (posX, posY, velocityX, velocityY) = it.destructured
                    Particle(
                        XYPosition(posX.asInt(), posY.asInt()),
                        Velocity(velocityX.asInt(), velocityY.asInt())
                    )
                }
        }

        private fun String.asInt() = trim().toInt()
    }

}


fun List<Particle>.move() = map { it.move() }

fun List<Particle>.getBounds() = Bounds(
    minOf { it.position.x },
    maxOf { it.position.x },
    minOf { it.position.y },
    maxOf { it.position.y }
)

fun List<Particle>.getStateWithMessage(): ParticlesState {
    val sequence = generateSequence(this) { it.move() }
        .zipWithNext()
        .takeWhile { (current, next) ->
            next.getBounds().maxX < current.getBounds().maxX
        }

    val asList = sequence.toList()

    return ParticlesState(asList.last().second, asList.size)
}


fun List<Particle>.draw(): String {
    val (minX, maxX, minY, maxY) = getBounds()
    return (minY..maxY).joinToString(separator = "\n") { y ->
        (minX..maxX).joinToString(separator = "") { x ->
            if (thereIsAParticleAt(x, y)) "#" else "."
        }
    }
}

private fun List<Particle>.thereIsAParticleAt(x: Int, y: Int) =
    any { it.position.x == x && it.position.y == y }
