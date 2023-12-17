package gg.jte.aoc.v2022

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class Day07Test {

    val input = """
        $ cd /
        $ ls
        dir a
        14848514 b.txt
        8504156 c.dat
        dir d
        $ cd a
        $ ls
        dir e
        29116 f
        2557 g
        62596 h.lst
        $ cd e
        $ ls
        584 i
        $ cd ..
        $ cd ..
        $ cd d
        $ ls
        4060174 j
        8033020 d.log
        5626152 d.ext
        7214296 k
    """.trimIndent()

    @Test
    fun `should parse files`() {
        val files = parseFiles(input)
        files shouldHaveSize 10
        with(files[0]) {
            path.shouldContainExactly("/")
            name shouldBe "b.txt"
            size shouldBe 14848514
        }
        with(files[1]) {
            path.shouldContainExactly("/")
            name shouldBe "c.dat"
            size shouldBe 8504156
        }
        with(files[2]) {
            path.shouldContainExactly("a", "/")
            name shouldBe "f"
            size shouldBe 29116
        }
        with(files[3]) {
            path.shouldContainExactly("a", "/")
            name shouldBe "g"
            size shouldBe 2557
        }
    }

    @Test
    fun `should find directories with size less than 100k`() {
        val directories = parseFiles(input)
            .directoriesWithSizeLessThan(sizeLimit = 100_000)

        directories shouldHaveSize 2
        with(directories[0]) {
            name shouldBe "/a"
            totalSize shouldBe 94853
        }
        with(directories[1]) {
            name shouldBe "/a/e"
            totalSize shouldBe 584
        }
    }

    @Test
    fun `find smallest directory to delete`() {
        val directory = parseFiles(input)
            .findSmallestDirectoryToDelete(freeSpaceNeeded = 30_000_000, totalDiskSpace = 70_000_000)

        directory shouldBe DirectorySummary(
            name = "/d",
            totalSize = 24933642
        )
    }

}
