package day01

import println
import readInput
import toIntWithWords

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { line -> "${line.first { it.isDigit() }}${line.last() { it.isDigit() }}".toLong() }
    }

    val digitWords = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    fun findFirstDigit(line: String): Int {
        val result = digitWords.map { word -> Pair(line.indexOf(word), word.toIntWithWords()) }.toMutableSet()
        val d = line.firstOrNull { it.isDigit() }
        if (d != null) {
            val dIndex = line.indexOf(d)
            result.add(Pair(dIndex, d.toString().toIntWithWords()))
        }
        return result.filter { it.first > -1 }.minBy { it.first }.second
    }

    fun findLastDigit(line: String): Int {
        val result = digitWords.map { word -> Pair(line.lastIndexOf(word), word.toIntWithWords()) }.toMutableSet()
        val d = line.lastOrNull() { it.isDigit() }
        if (d != null) {
            val dIndex = line.lastIndexOf(d)
            result.add(Pair(dIndex, d.toString().toIntWithWords()))
        }
        return result.filter { it.first > -1 }.maxBy { it.first }.second
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            "${findFirstDigit(line)}${findLastDigit(line)}".toLong()
        }
    }

    "Result Test 1 = ${part1(readInput("day01/Day01_1_test1"))}".println()
    "Result Part 1 = ${part1(readInput("day01/Day01_1"))}".println()

    "Result Test 2 = ${part2(readInput("day01/Day01_2_test2"))}".println()
    "Result Part 2 = ${part2(readInput("day01/Day01_2"))}".println()
}
