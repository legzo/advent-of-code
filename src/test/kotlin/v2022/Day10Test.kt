package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day10Test {

    private val input = """
        noop
        addx 3
        addx -5
    """.trimIndent()

    @Test
    fun `should parse as cycles`() {
        parseAsCycles(input) shouldBe listOf(
            NoopCycle,
            NoopCycle,
            CycleWithEffect(3),
            NoopCycle,
            CycleWithEffect(-5),
        )
    }

    @Test
    fun `should get signal strength for larger input`() {
        val signalValues = parseAsCycles(input2).signalValues()
        signalValues.signalStrength(atCycle = 20) shouldBe 21 * 20
        signalValues.signalStrength(atCycle = 60) shouldBe 19 * 60
        signalValues.signalStrength(atCycle = 100) shouldBe 18 * 100
        signalValues.signalStrength(atCycle = 140) shouldBe 21 * 140
        signalValues.signalStrength(atCycle = 180) shouldBe 16 * 180
        signalValues.signalStrength(atCycle = 220) shouldBe 18 * 220
    }

    @Test
    fun `should get sum of signal strength`() {
        parseAsCycles(input2).sumOfSignalStrengths() shouldBe 13140
    }

    @Test
    fun `should draw signal`() {
        parseAsCycles(input2).signalValues().draw() shouldBe """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
            
            """.trimIndent()
    }

    private val input2 = """
        addx 15
        addx -11
        addx 6
        addx -3
        addx 5
        addx -1
        addx -8
        addx 13
        addx 4
        noop
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx 5
        addx -1
        addx -35
        addx 1
        addx 24
        addx -19
        addx 1
        addx 16
        addx -11
        noop
        noop
        addx 21
        addx -15
        noop
        noop
        addx -3
        addx 9
        addx 1
        addx -3
        addx 8
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        addx -36
        noop
        addx 1
        addx 7
        noop
        noop
        noop
        addx 2
        addx 6
        noop
        noop
        noop
        noop
        noop
        addx 1
        noop
        noop
        addx 7
        addx 1
        noop
        addx -13
        addx 13
        addx 7
        noop
        addx 1
        addx -33
        noop
        noop
        noop
        addx 2
        noop
        noop
        noop
        addx 8
        noop
        addx -1
        addx 2
        addx 1
        noop
        addx 17
        addx -9
        addx 1
        addx 1
        addx -3
        addx 11
        noop
        noop
        addx 1
        noop
        addx 1
        noop
        noop
        addx -13
        addx -19
        addx 1
        addx 3
        addx 26
        addx -30
        addx 12
        addx -1
        addx 3
        addx 1
        noop
        noop
        noop
        addx -9
        addx 18
        addx 1
        addx 2
        noop
        noop
        addx 9
        noop
        noop
        noop
        addx -1
        addx 2
        addx -37
        addx 1
        addx 3
        noop
        addx 15
        addx -21
        addx 22
        addx -6
        addx 1
        noop
        addx 2
        addx 1
        noop
        addx -10
        noop
        noop
        addx 20
        addx 1
        addx 2
        addx 2
        addx -6
        addx -11
        noop
        noop
        noop
    """.trimIndent()

}
