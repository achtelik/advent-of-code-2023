package day08

import println
import readInput

fun main() {
    val day = "08"
    //"Result Test 1 = ${Day08Solution.solve1(readInput("day$day/1_test1"))}".println()
    //"Result Test 1 = ${Day08Solution.solve1(readInput("day$day/1_test2"))}".println()
    //"Result Part 1 = ${Day08Solution.solve1(readInput("day$day/1"))}".println()

    //"Result Test 2 = ${Day08Solution.solve2(readInput("day$day/2_test1"))}".println()
    "Result Part 2 = ${Day08Solution.solve2(readInput("day$day/2"))}".println()
}

class Day08Solution {
    companion object {
        fun solve1(input: List<String>): Long {
            val sequence = input[0].split("").filter { it.isNotEmpty() }
            val mapEntries = mutableMapOf<String, Pair<String, String>>()

            input.subList(2, input.size).forEach {
                mapEntries[it.substring(0, 3)] = Pair(it.substring(7, 10), it.substring(12, 15))
            }
            return calculateStpes1(sequence, mapEntries)
        }

        fun calculateStpes1(sequence: List<String>, mapEntries: MutableMap<String, Pair<String, String>>): Long {
            var position = "AAA"
            var steps = 0L
            var index = 0
            while (position != "ZZZ") {
                position = when (sequence[index]) {
                    "L" -> mapEntries[position]!!.first
                    "R" -> mapEntries[position]!!.second
                    else -> throw IllegalArgumentException("ERROR")
                }
                index++
                if (index == sequence.size) index = 0
                steps++
            }
            return steps
        }

        fun solve2(input: List<String>): Long {
            val sequence = input[0].split("").filter { it.isNotEmpty() }
            val mapEntries = mutableMapOf<String, Pair<String, String>>()

            input.subList(2, input.size).forEach {
                mapEntries[it.substring(0, 3)] = Pair(it.substring(7, 10), it.substring(12, 15))
            }

            var positions = mapEntries.keys.filter { it.endsWith("A") }

            val steps = positions.map {
                var position = it
                var steps = 0L
                var index = 0
                while (!position.endsWith("Z")) {
                    position = when (sequence[index]) {
                        "L" -> mapEntries[position]!!.first
                        "R" -> mapEntries[position]!!.second
                        else -> throw IllegalArgumentException("ERROR")
                    }
                    index++
                    if (index == sequence.size) index = 0
                    steps++
                }
                steps
            }

            println(steps)

            println(steps.reduce { acc, l -> acc * l })

            var minStep = steps.min()
            var result = steps.min()
            while (!steps.all { result % it == 0L }) result += minStep
            return result
        }
    }
}