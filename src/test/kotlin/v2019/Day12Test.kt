package gg.jte.aoc.v2019

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun `should apply velocity`() {
        val result = Moon(Position(1, 2, 3), Velocity(-2, 0, 3)).applyVelocity()
        result shouldBe Moon(Position(-1, 2, 6), Velocity(-2, 0, 3))

        AstralSystem(
            listOf(
                Moon(Position(3, 0, 0), Velocity(1, 0, 0)),
                Moon(Position(5, 0, 0), Velocity(-1, 0, 0))
            )
        ).applyVelocity().moons shouldBe listOf(
            Moon(Position(4, 0, 0), Velocity(1, 0, 0)),
            Moon(Position(4, 0, 0), Velocity(-1, 0, 0))
        )
    }

    @Test
    fun `should apply gravity`() {
        AstralSystem(
            listOf(
                Moon(Position(3, 0, 0), Velocity(0, 0, 0)),
                Moon(Position(5, 0, 0), Velocity(0, 0, 0))
            )
        ).applyGravity().moons shouldBe listOf(
            Moon(Position(3, 0, 0), Velocity(1, 0, 0)),
            Moon(Position(5, 0, 0), Velocity(-1, 0, 0))
        )
    }

    @Test
    fun `should parse moons`() {
        AstralSystem.of(
            """
            pos=<x= 2, y=-1, z= 1>, vel=<x= 3, y=-1, z=-1>
            pos=<x= 3, y=-7, z=-4>, vel=<x= 1, y= 3, z= 3>
            pos=<x= 1, y=-7, z= 5>, vel=<x=-3, y= 1, z=-3>
            pos=<x= 2, y= 2, z= 0>, vel=<x=-1, y=-3, z= 1>
        """.trimIndent()
        ) shouldBe AstralSystem(
            listOf(
                Moon(Position(2, -1, 1), Velocity(3, -1, -1)),
                Moon(Position(3, -7, -4), Velocity(1, 3, 3)),
                Moon(Position(1, -7, 5), Velocity(-3, 1, -3)),
                Moon(Position(2, 2, 0), Velocity(-1, -3, 1))
            )
        )
    }

    @Test
    fun `should parse moons for other format`() {
        AstralSystem.of(
            """
            <x=17, y=-12, z=13>
            <x=2, y=1, z=1>
            <x=-1, y=-17, z=7>
            <x=12, y=-14, z=18>
        """.trimIndent()
        ) shouldBe AstralSystem(
            listOf(
                Moon(Position(17, -12, 13), Velocity(0,0,0)),
                Moon(Position(2, 1, 1), Velocity(0,0,0)),
                Moon(Position(-1, -17, 7), Velocity(0,0,0)),
                Moon(Position(12, -14, 18), Velocity(0,0,0))
            )
        )
    }

    @Test
    fun `should step correctly`() {
        val system = AstralSystem.of(
            """
            pos=<x=-1, y=  0, z= 2>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 2, y=-10, z=-7>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 4, y= -8, z= 8>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 3, y=  5, z=-1>, vel=<x= 0, y= 0, z= 0>
        """.trimIndent()
        )

        system.step(10).moons shouldBe listOf(
            Moon(Position(2, 1, -3), Velocity(-3, -2, 1)),
            Moon(Position(1, -8, 0), Velocity(-1, 1, 3)),
            Moon(Position(3, -6, 1), Velocity(3, 2, -3)),
            Moon(Position(2, 0, 4), Velocity(1, -1, -1))
        )
    }

    @Test
    fun `should calculate energy of system`() {
        AstralSystem.of(
            """
            pos=<x= 2, y= 1, z=-3>, vel=<x=-3, y=-2, z= 1>
            pos=<x= 1, y=-8, z= 0>, vel=<x=-1, y= 1, z= 3>
            pos=<x= 3, y=-6, z= 1>, vel=<x= 3, y= 2, z=-3>
            pos=<x= 2, y= 0, z= 4>, vel=<x= 1, y=-1, z=-1>
        """.trimIndent()
        ).energy shouldBe 179
    }
}





