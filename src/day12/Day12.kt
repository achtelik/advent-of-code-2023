package day12

import kotlinx.coroutines.*
import println
import readInput
import java.lang.Exception


fun main() {
    val day = "12"
    runBlocking(Dispatchers.Default) {
        //"Result Test 1-1 = ${Day12Solution1().solve(readInput("day$day/1_test1"))}".println()
        //"Result Part 1 = ${Day12Solution1().solve(readInput("day$day/1"))}".println()

        "Result Test 2-1 = ${Day12Solution2().solve(readInput("day$day/2_test1"))}".println() // 4
        //"Result Part 2 = ${Day12Solution2().solve(readInput("day$day/2"))}".println()
    }
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

    data class Input(val input: String, val validation: String, val possibilities: List<String>)

    suspend fun solve(input: List<String>): Long {
        val inputData = createInput(input)
        return inputData.sumOf { it.possibilities.size }.toLong()
    }

    suspend fun createInput(input: List<String>): List<Input> {
        return coroutineScope {
            input.map {
                async {
                    val data = it.split(" ")[0].repeat(3, "?")
                    val validation = it.split(" ")[1].repeat(3, ",")
                    println("Processing: $data")
                    val result = Input(data, validation, replaceQuestionMark(data, validation))
                    println("Processed: $data")
                    result
                }
            }.awaitAll()
        }
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

    private fun String.repeat(n: Int, separator: String): String {
        val new = "$this$separator".repeat(n)
        return new.substring(0, new.lastIndexOf(separator))
    }
}