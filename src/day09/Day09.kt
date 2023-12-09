package day09

import println
import readInput

fun main() {
    val day = "09"
    //"Result Test 1 = ${Day09Solution.solve1(readInput("day$day/1_test1"))}".println()
    //"Result Part 1 = ${Day09Solution.solve1(readInput("day$day/1"))}".println()

    //"Result Test 2 = ${Day09Solution.solve2(readInput("day$day/2_test1"))}".println()
    "Result Part 2 = ${Day09Solution.solve2(readInput("day$day/2"))}".println()
}

class Day09Solution {
    companion object {
        fun solve1(input: List<String>): Long {
            val result = input.map { inputLine ->
                var currentData = inputLine.split(" ").map { it.toLong() }.toMutableList()
                val rowData = mutableListOf<List<Long>>()
                rowData.add(currentData)
                while (currentData.any { it != 0L }) {
                    val newData = mutableListOf<Long>()
                    for (i in 0..<currentData.size - 1) {
                        newData.add(currentData[i + 1] - currentData[i])
                    }
                    currentData = newData
                    rowData.add(newData)
                }
                rowData.sumOf { it.last() }
            }
            return result.sumOf { it }
        }


        fun solve2(input: List<String>): Long {
            val result = input.map { inputLine ->
                var currentData = inputLine.split(" ").map { it.toLong() }.toMutableList()
                val rowData = mutableListOf<MutableList<Long>>()
                rowData.add(currentData)
                while (currentData.any { it != 0L }) {
                    val newData = mutableListOf<Long>()
                    for (i in 0..<currentData.size - 1) {
                        newData.add(currentData[i + 1] - currentData[i])
                    }
                    currentData = newData
                    rowData.add(newData)
                }
                for (i in rowData.size - 1 downTo 0) {
                    when {
                        i == rowData.size - 1 -> rowData[i].add(0, 0L)
                        else -> rowData[i].add(0, rowData[i][0] - rowData[i + 1][0])
                    }
                }
                rowData.first()[0]
            }
            return result.sumOf { it }
        }
    }
}