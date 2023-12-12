package day12

import println
import readInput
import java.lang.Exception


fun main() {
    val day = "12"
    //"Result Test 1-1 = ${Day12Solution1().solve(readInput("day$day/1_test1"))}".println()
    //"Result Part 1 = ${Day12Solution1().solve(readInput("day$day/1"))}".println()

    "Result Test 2-1 = ${Day12Solution2().solve(readInput("day$day/2_test1"))}".println() // 4
    //"Result Part 2 = ${Day12Solution2().solve(readInput("day$day/2"))}".println()
}

class Day12Solution1 {

    data class Input(val input: String, val validation: String, val possibilities: List<String>)

    fun solve(input: List<String>): Long {
        val inputData = createInput(input)
        return inputData.sumOf { it.possibilities.size }.toLong()
    }

    fun createInput(input: List<String>): List<Input> {
        return input.parallelStream().map {
            val data = it.split(" ")[0]
            val validation = it.split(" ")[1]
            Input(data, validation, replaceQuestionMark(data, validation))
        }.toList()
    }

    fun replaceQuestionMark(input: String, validation: String): List<String> {
        if (!input.contains("?")) {
            return when {
                checkInputIsValid(input, validation) -> listOf(input)
                else -> listOf("")
            }
        }
        val result = mutableListOf<String>()
        result.addAll(replaceQuestionMark(input.replaceFirst("?", "."), validation).filter { it.isNotEmpty() })
        result.addAll(replaceQuestionMark(input.replaceFirst("?", "#"), validation).filter { it.isNotEmpty() })
        return result
    }

    fun checkInputIsValid(input: String, validation: String): Boolean {
        val damagedSpringGroups = input.split(".").filter { it.isNotEmpty() }
        val validationGroups = validation.split(",")
        return try {
            validationGroups.size == damagedSpringGroups.size &&
                    validationGroups.mapIndexed { index, string -> string.toInt() == damagedSpringGroups[index].length }
                        .all { it }
        } catch (ex: Exception) {
            false
        }
    }
}

class Day12Solution2 {

    data class Input(
        val extended: Int,
        val data: String,
        val dataExtended: String,
        val validation: String,
        val validationExtended: String,
        val possibilities: List<String>
    )

    fun solve(input: List<String>): Long {
        val input = createInput(input)
        return input.sumOf { it.possibilities.size }.toLong()
    }

    fun createInput(input: List<String>): List<Input> {
        return input.parallelStream().map {
            System.out.println(Thread.currentThread().getName())
            val data = it.split(" ")[0]
            val validation = it.split(" ")[1]
            Input(
                1,
                data,
                data.extend('?', '.'),
                validation,
                validation.extend(',', ','), replaceQuestionMark(
                    data,
                    validation
                )
            )
        }.toList()
    }

    fun replaceQuestionMark(input: String, validation: String): List<String> {
        if (!input.contains("?")) {
            return when {
                checkInputIsValid(input, validation) -> listOf(input)
                else -> listOf("")
            }
        }
        val result = mutableListOf<String>()
        result.addAll(replaceQuestionMark(input.replaceFirst("?", "."), validation).filter { it.isNotEmpty() })
        result.addAll(replaceQuestionMark(input.replaceFirst("?", "#"), validation).filter { it.isNotEmpty() })
        return result
    }

    fun checkInputIsValid(input: String, validation: String): Boolean {
        val damagedSpringGroups = input.split(".").filter { it.isNotEmpty() }
        val validationGroups = validation.split(",")
        return try {
            validationGroups.size == damagedSpringGroups.size &&
                    validationGroups.mapIndexed { index, string -> string.toInt() == damagedSpringGroups[index].length }
                        .all { it }
        } catch (ex: Exception) {
            false
        }
    }

    private fun String.extend(addition: Char, seperator: Char): String {
        val extension = this.substring(0, this.indexOfFirst { it == seperator })
        val new = "$this$addition$extension"
        return new
    }

    private fun String.extend(addition: Char): String {
        return "$this$addition$this"
    }
}