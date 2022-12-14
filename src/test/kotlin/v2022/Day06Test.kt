package gg.jte.aoc.v2022

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun `should find first marker index`() {
        findFirstMarkerIndex("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 7
        findFirstMarkerIndex("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 5
        findFirstMarkerIndex("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 6
        findFirstMarkerIndex("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 10
        findFirstMarkerIndex("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 11
    }

    @Test
    fun `should find first message index`() {
        findFirstMessageIndex("mjqjpqmgbljsphdztnvjfqwrcgsmlb") shouldBe 19
        findFirstMessageIndex("bvwbjplbgvbhsrlpgdmjqwftvncz") shouldBe 23
        findFirstMessageIndex("nppdvjthqldpwncqszvftbrmjlhg") shouldBe 23
        findFirstMessageIndex("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") shouldBe 29
        findFirstMessageIndex("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") shouldBe 26
    }
}
