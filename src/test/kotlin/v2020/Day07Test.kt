package gg.jte.aoc.v2020

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day07Test {

    private val input = """
        light red bags contain 1 bright white bag, 2 muted yellow bags.
        dark orange bags contain 3 bright white bags, 4 muted yellow bags.
        bright white bags contain 1 shiny gold bag.
        muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
        shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
        dark olive bags contain 3 faded blue bags, 4 dotted black bags.
        vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
        faded blue bags contain no other bags.
        dotted black bags contain no other bags.
        """.trimIndent()

    private val bagConstraints = input
        .split('\n')
        .mapNotNull { line -> parseAsBagConstraint(line) }

    @Test
    fun `should parse lines correctly`() {
        bagConstraints shouldBe listOf(
            BagConstraint(
                container = Bag(color = "light red"),
                contents = listOf(
                    Content(quantity = 1, bag = Bag(color = "bright white")),
                    Content(quantity = 2, bag = Bag(color = "muted yellow"))
                )
            ),
            BagConstraint(
                container = Bag(color = "dark orange"),
                contents = listOf(
                    Content(quantity = 3, bag = Bag(color = "bright white")),
                    Content(quantity = 4, bag = Bag(color = "muted yellow"))
                )
            ),
            BagConstraint(
                container = Bag(color = "bright white"),
                contents = listOf(
                    Content(quantity = 1, bag = Bag(color = "shiny gold"))
                )
            ),
            BagConstraint(
                container = Bag(color = "muted yellow"),
                contents = listOf(
                    Content(quantity = 2, bag = Bag(color = "shiny gold")),
                    Content(quantity = 9, bag = Bag(color = "faded blue"))
                )
            ),
            BagConstraint(
                container = Bag(color = "shiny gold"),
                contents = listOf(
                    Content(quantity = 1, bag = Bag(color = "dark olive")),
                    Content(quantity = 2, bag = Bag(color = "vibrant plum"))
                )
            ),
            BagConstraint(
                container = Bag(color = "dark olive"),
                contents = listOf(
                    Content(quantity = 3, bag = Bag(color = "faded blue")),
                    Content(quantity = 4, bag = Bag(color = "dotted black"))
                )
            ),
            BagConstraint(
                container = Bag(color = "vibrant plum"),
                contents = listOf(
                    Content(quantity = 5, bag = Bag(color = "faded blue")),
                    Content(quantity = 6, bag = Bag(color = "dotted black"))
                )
            ),
            BagConstraint(
                container = Bag(color = "faded blue"),
                contents = listOf()
            ),
            BagConstraint(
                container = Bag(color = "dotted black"),
                contents = listOf()
            )
        )
    }

    @Test
    fun `should find all possible containers`() {
        val shinyGoldBag = Bag("shiny gold")
        val allContainersForShinyGold = bagConstraints.findAllContainersFor(shinyGoldBag)
        allContainersForShinyGold shouldBe setOf(
            Bag(color = "bright white"),
            Bag(color = "muted yellow"),
            Bag(color = "dark orange"),
            Bag(color = "light red")
        )
    }

    @Test
    fun `should count all possible contained bags`() {
        val shinyGoldBag = Bag("shiny gold")
        bagConstraints.countAllPossibleContainedBagsFor(shinyGoldBag) shouldBe 32

        val otherInput = """
            shiny gold bags contain 2 dark red bags.
            dark red bags contain 2 dark orange bags.
            dark orange bags contain 2 dark yellow bags.
            dark yellow bags contain 2 dark green bags.
            dark green bags contain 2 dark blue bags.
            dark blue bags contain 2 dark violet bags.
            dark violet bags contain no other bags.
            """.trimIndent()

        val otherBagConstraints = otherInput
            .split('\n')
            .mapNotNull { line -> parseAsBagConstraint(line) }

        otherBagConstraints.countAllPossibleContainedBagsFor(shinyGoldBag) shouldBe 126
    }
}