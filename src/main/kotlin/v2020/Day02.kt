package gg.jte.aoc.v2020

import gg.jte.aoc.getLinesFromFile
import gg.jte.aoc.measureTimeAndPrint

fun main() {

    val input = getLinesFromFile("v2020/day_02.txt")

    // Part 1
    measureTimeAndPrint { input.countCompliantPasswords(PasswordPolicy.Old) }

    // Part 2
    measureTimeAndPrint { input.countCompliantPasswords(PasswordPolicy.New) }

}

sealed class PasswordPolicy {

    abstract fun parse(input: String): RuleAndPassword?
    internal val parsingRegex = Regex("([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)")

    object Old : PasswordPolicy() {
        override fun parse(input: String): RuleAndPassword? {
            val matchResult = parsingRegex.matchEntire(input)

            return if (matchResult != null) {
                val (minAsString, maxAsString, char, password) = matchResult.destructured

                RuleAndPassword(
                    PasswordRule.Old(
                        minOccurrences = minAsString.toInt(),
                        maxOccurrences = maxAsString.toInt(),
                        character = char[0]
                    ),
                    password
                )

            } else null
        }
    }

    object New : PasswordPolicy() {
        override fun parse(input: String): RuleAndPassword? {
            val matchResult = parsingRegex.matchEntire(input)

            return if (matchResult != null) {
                val (firstIndexAsString, secondIndexAsString, char, password) = matchResult.destructured

                RuleAndPassword(
                    PasswordRule.New(
                        firstIndex = firstIndexAsString.toInt() - 1,
                        secondIndex = secondIndexAsString.toInt() - 1,
                        character = char[0]
                    ),
                    password
                )

            } else null
        }
    }
}

sealed class PasswordRule {

    abstract fun validate(password: String): Boolean

    data class Old(
        val minOccurrences: Int,
        val maxOccurrences: Int,
        val character: Char
    ) : PasswordRule() {
        override fun validate(password: String) =
            password.count { it == character } in (minOccurrences..maxOccurrences)

    }

    data class New(
        val firstIndex: Int,
        val secondIndex: Int,
        val character: Char
    ) : PasswordRule() {
        override fun validate(password: String) =
            (password[firstIndex] == character) xor (password[secondIndex] == character)

    }
}

data class RuleAndPassword(
    val rule: PasswordRule,
    val password: String
)

fun List<String>.countCompliantPasswords(passwordPolicy: PasswordPolicy) =
    findCompliantPasswords(passwordPolicy).size

fun List<String>.findCompliantPasswords(passwordPolicy: PasswordPolicy): List<String> {
    return mapNotNull { line -> passwordPolicy.parse(input = line) }
        .filter { (rule, password) -> rule.validate(password) }
        .map { (_, password) -> password }
}
