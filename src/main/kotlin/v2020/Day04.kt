package gg.jte.aoc.v2020

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {
    val input = getTextFromFile("v2020/day_04.txt")

    // Part 1
    measureTimeAndPrint { input.countValidPassportsItems() }

    // Part 2
    measureTimeAndPrint { input.countValidPassportsItemsWithFieldsValidation() }
}

val heightRegex = Regex("([0-9]+)(cm|in)")
val hairColorRegex = Regex("#[0-9a-f]{6}")
val passportIdRegex = Regex("[0-9]{9}")

fun byShortLabel(shortLabel: String): PassportField? =
    PassportField.entries.firstOrNull { it.shortLabel == shortLabel }

enum class PassportField(
    val shortLabel: String,
    val validate: (String) -> Boolean,
    val optional: Boolean = false,
) {
    BirthYear("byr", validate = isIntBetween(1920, 2002)),
    IssueYear("iyr", validate = isIntBetween(2010, 2020)),
    ExpirationYear("eyr", validate = isIntBetween(2020, 2030)),
    Height("hgt", validate = {
        val matchResult = heightRegex.matchEntire(it)
        if (matchResult != null) {
            val (heightValue, unit) = matchResult.destructured
            when (unit) {
                "cm" -> isIntBetween(150, 193)(heightValue)
                "in" -> isIntBetween(59, 76)(heightValue)
                else -> false
            }
        } else false
    }),
    HairColor("hcl", validate = { hairColorRegex.matches(it) }),
    EyeColor("ecl", validate = { it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }),
    PassportId("pid", validate = { passportIdRegex.matches(it) }),
    CountryId("cid", validate = { true }, optional = true);

}

fun String.countValidPassportsItems() =
    parsePassports()
        .count { it.isStructurallyValid() }

fun String.countValidPassportsItemsWithFieldsValidation() =
    parsePassports()
        .count {
            it.isStructurallyValid() && it.allFieldsAreValid()
        }

fun String.parsePassports() =
    this.split("\n\n")
        .map { passportAsString ->
            Passport(fields = extractFields(passportAsString))
        }


private fun isIntBetween(lowerBound: Int, upperBound: Int): (String) -> Boolean {
    return { value -> value.toIntOrNull() in lowerBound..upperBound }
}

private fun extractFields(passportAsString: String) =
    passportAsString
        .split(' ', '\n')
        .mapNotNull {
            val keyValues = it.split(':')
            if (keyValues.size != 2) return@mapNotNull null
            val key = keyValues[0]
            val value = keyValues[1]
            val field = byShortLabel(key)
            if (field != null) {
                field to value
            } else null
        }.toMap()

data class Passport(
    val fields: Map<PassportField, String>
) {
    fun isStructurallyValid() =
        PassportField.entries
            .all { it.optional || (fields[it]?.isNotBlank() ?: false) }

    fun allFieldsAreValid() =
        PassportField.entries
            .all {
                it.optional
                        || fields[it]?.let { value -> it.validate(value) } ?: false
            }
}
