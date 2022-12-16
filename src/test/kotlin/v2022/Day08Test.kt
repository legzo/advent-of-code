package gg.jte.aoc.v2022

import gg.jte.aoc.v2022.Direction.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    private val input = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent()

    private val mapOfTrees = MapOfTrees(input)

    @Test
    fun `should return char at`() {
        mapOfTrees.treeAt(x = 0, y = 0) shouldBe 3
        mapOfTrees.treeAt(x = 4, y = 2) shouldBe 2
        mapOfTrees.treeAt(x = 4, y = 3) shouldBe 9
    }

    @Test
    fun `should get dimensions`() {
        mapOfTrees.height shouldBe 5
        mapOfTrees.width shouldBe 5
    }

    @Test
    fun `should get no trees for borders`() {
        mapOfTrees.trees(direction = UP, of = Tree(x = 0, y = 0)) shouldBe listOf()
        mapOfTrees.trees(direction = LEFT, of = Tree(x = 0, y = 0)) shouldBe listOf()
        mapOfTrees.trees(direction = RIGHT, of = Tree(x = 4, y = 0)) shouldBe listOf()
        mapOfTrees.trees(direction = DOWN, of = Tree(x = 0, y = 4)) shouldBe listOf()
        mapOfTrees.trees(direction = LEFT, of = Tree(x = 0, y = 4)) shouldBe listOf()
    }

    @Test
    fun `should get trees for direction`() {
        mapOfTrees.trees(direction = UP, of = Tree(x = 3, y = 3)) shouldBe listOf(7, 1, 3)
        mapOfTrees.trees(direction = DOWN, of = Tree(x = 3, y = 3)) shouldBe listOf(9)
        mapOfTrees.trees(direction = LEFT, of = Tree(x = 3, y = 3)) shouldBe listOf(3, 3, 5)
        mapOfTrees.trees(direction = RIGHT, of = Tree(x = 3, y = 3)) shouldBe listOf(9)
    }

    @Test
    fun `all trees on the border should be visible`() {
        with(mapOfTrees) {
            Tree(x = 0, y = 0).isVisible() shouldBe true
            Tree(x = 0, y = 1).isVisible() shouldBe true
            Tree(x = 0, y = 2).isVisible() shouldBe true
            Tree(x = 0, y = 3).isVisible() shouldBe true
            Tree(x = 4, y = 0).isVisible() shouldBe true
            Tree(x = 4, y = 2).isVisible() shouldBe true
            Tree(x = 4, y = 4).isVisible() shouldBe true
            Tree(x = 0, y = 4).isVisible() shouldBe true
            Tree(x = 1, y = 4).isVisible() shouldBe true
            Tree(x = 2, y = 4).isVisible() shouldBe true
        }
    }

    @Test
    fun `should calculate visibility for inner trees`() {
        with(mapOfTrees) {
            Tree(x = 1, y = 1).isVisible() shouldBe true
            Tree(x = 2, y = 1).isVisible() shouldBe true
            Tree(x = 3, y = 1).isVisible() shouldBe false
            Tree(x = 1, y = 2).isVisible() shouldBe true
            Tree(x = 2, y = 2).isVisible() shouldBe false
            Tree(x = 3, y = 2).isVisible() shouldBe true
        }
    }

    @Test
    fun `should count visible trees`() {
        mapOfTrees.countVisibleTrees() shouldBe 21
    }
}

