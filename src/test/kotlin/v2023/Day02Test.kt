package v2023

import gg.jte.aoc.v2023.Draw
import gg.jte.aoc.v2023.Game
import gg.jte.aoc.v2023.isPossible
import gg.jte.aoc.v2023.parseAsGames
import gg.jte.aoc.v2023.power
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day02Test {

    private val input = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()

    @Test
    fun `parse game line`() {
        input.parseAsGames() shouldHaveSize 5
        input.parseAsGames().first() shouldBe Game(
            id = 1,
            draws = listOf(
                Draw(
                    red = 4,
                    blue = 3,
                ),
                Draw(
                    red = 1,
                    green = 2,
                    blue = 6,
                ),
                Draw(
                    green = 2,
                ),
            )
        )
    }

    @Test
    fun `a game is possible`() {
        input.parseAsGames()
            .filter { it.isPossible() } shouldHaveSize 3
    }

    @Test
    fun `calculate power`() {
        input.parseAsGames().map { it.power() } shouldBe listOf(48, 12, 1560, 630, 36)
    }
}
