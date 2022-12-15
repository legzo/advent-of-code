package gg.jte.aoc.v2022

import gg.jte.aoc.getTextFromFile
import gg.jte.aoc.measureTimeAndPrint
import gg.jte.aoc.parseWithRegex
import kotlin.collections.ArrayDeque

fun main() {
    val input = getTextFromFile("v2022/day07.txt")
    measureTimeAndPrint {
        parseFiles(input)
            .directoriesWithSizeLessThan(100_000)
            .sumOf { it.totalSize }
    }

}

fun List<FileWithPath>.directoriesWithSizeLessThan(sizeLimit: Int): List<DirectorySummary> {
    val directoriesWithSize = mutableMapOf<String, Int>()

    forEach { fileWithPath ->
        addSizeToDirectory(directoriesWithSize, fileWithPath.size, fileWithPath.path)
    }

    return directoriesWithSize.filterValues { it < sizeLimit }
        .map { (name, totalSize) -> DirectorySummary(name = name, totalSize = totalSize) }
        .sortedBy { it.name }
}

fun addSizeToDirectory(
    directoriesWithSize: MutableMap<String, Int>,
    size: Int,
    path: ArrayDeque<String>
) {
    val filepath = path.asFilepath()

    directoriesWithSize.merge(filepath, size, Int::plus)
    path.removeFirst()
    if (path.isNotEmpty())
        addSizeToDirectory(directoriesWithSize, size, path)
}

fun parseFiles(input: String): List<FileWithPath> =
    with(ArrayDeque<String>()) {
        input.lines().mapNotNull { line ->
            when (val parsedLine = line.parseLine()) {
                is PopDirectory -> removeFirst().run { null }
                is ChangeDirectory -> addFirst(parsedLine.target).run { null }
                null, is Directory, ListContents -> null
                is File -> parsedLine.withPath(ArrayDeque(this))
            }
        }
    }


private fun String.parseLine(): Line? =
    parseWithRegex("dir (\\w+)") { (dirName) -> Directory(dirName) }
        ?: parseWithRegex("\\$ cd ..") { PopDirectory }
        ?: parseWithRegex("\\$ cd (.+)") { (targetDirectory) -> ChangeDirectory(targetDirectory) }
        ?: parseWithRegex("\\$ ls") { ListContents }
        ?: parseWithRegex("(\\d+) ([\\w.]+)") { (size, fileName) -> File(name = fileName, size = size.toInt()) }

sealed interface Line


data class Directory(val name: String) : Line
data class File(val name: String, val size: Int) : Line
data class FileWithPath(val name: String, val path: ArrayDeque<String>, val size: Int)
sealed interface Command : Line
object PopDirectory : Command
data class ChangeDirectory(val target: String) : Command
object ListContents : Command

data class DirectorySummary(val name: String, val totalSize: Int)

fun File.withPath(path: ArrayDeque<String>) =
    FileWithPath(
        name = name,
        path = path,
        size = size,
    )

fun ArrayDeque<String>.asFilepath() =
    reversed().joinToString(separator = "/") { it }.replace("//", "/")