package gg.jte.aoc.v2020

import gg.jte.aoc.v2020.PassportField.BirthYear
import gg.jte.aoc.v2020.PassportField.CountryId
import gg.jte.aoc.v2020.PassportField.ExpirationYear
import gg.jte.aoc.v2020.PassportField.EyeColor
import gg.jte.aoc.v2020.PassportField.HairColor
import gg.jte.aoc.v2020.PassportField.Height
import gg.jte.aoc.v2020.PassportField.IssueYear
import gg.jte.aoc.v2020.PassportField.PassportId
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun `should validate passport`() {
        Passport(
            fields = mapOf(
                BirthYear to "1984"
            )
        ).isStructurallyValid() shouldBe false

        Passport(
            fields = mapOf(
                BirthYear to "1984",
                IssueYear to "v2020",
                ExpirationYear to "2048",
                Height to "194",
                HairColor to "blond",
                EyeColor to "green",
                PassportId to "ISDJSPJDPS",
                CountryId to "FR"
            )
        ).isStructurallyValid() shouldBe true

        Passport(
            fields = mapOf(
                BirthYear to "1984",
                IssueYear to "v2020",
                ExpirationYear to "2048",
                Height to "194",
                HairColor to "blond",
                EyeColor to "green",
                PassportId to "ISDJSPJDPS"
            )
        ).isStructurallyValid() shouldBe true
    }

    @Test
    fun `should count valid passports`() {
        val input = """
            ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
            byr:1937 iyr:2017 cid:147 hgt:183cm
            
            iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
            hcl:#cfa07d byr:1929
            
            hcl:#ae17e1 iyr:2013
            eyr:2024
            ecl:brn pid:760753108 byr:1931
            hgt:179cm
            
            hcl:#cfa07d eyr:2025 pid:166559648
            iyr:2011 ecl:brn hgt:59in""".trimIndent()

        input.countValidPassportsItems() shouldBe 2
    }

    @Test
    fun `should not validate passwords because of field format`() {
        val input = """
            eyr:1972 cid:100
            hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926
            
            iyr:2019
            hcl:#602927 eyr:1967 hgt:170cm
            ecl:grn pid:012533040 byr:1946
            
            hcl:dab227 iyr:2012
            ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277
            
            hgt:59cm ecl:zzz
            eyr:2038 hcl:74454a iyr:2023
            pid:3556412378 byr:2007
            """.trimIndent()

        input.countValidPassportsItemsWithFieldsValidation() shouldBe 0
    }

    @Test
    fun `should validate all passwords because of field format`() {
        val input = """
            pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
            hcl:#623a2f

            eyr:2029 ecl:blu cid:129 byr:1989
            iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

            hcl:#888785
            hgt:164cm byr:2001 iyr:2015 cid:88
            pid:545766238 ecl:hzl
            eyr:2022

            iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
            """.trimIndent()

        input.countValidPassportsItemsWithFieldsValidation() shouldBe 4
    }

}
