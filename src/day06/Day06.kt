package day03

import AOCInputDataTable
import println
import readAOCInputDataTable
import readInput

fun main() {
    val day = "06"
    //"Result Test 1 = ${Day06Solution.solve1(readInput("day$day/1_test1"))}".println()
    //"Result Part 1 = ${Day06Solution.solve1(readInput("day$day/1"))}".println()

    //"Result Test 2 = ${Day06Solution.solve2(readInput("day$day/2_test1"))}".println()
    "Result Part 2 = ${Day06Solution.solve2(readInput("day$day/2"))}".println()
}

private class Day06Solution {
    companion object {
        fun solve1(input: List<String>): Int {
            val times =
                input[0].replace("Time:", "").trim().split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }
            val distanceRecords =
                input[1].replace("Distance:", "").trim().split(" ").filter { it.isNotEmpty() }.map { it.trim().toInt() }

            var solutions = 1
            for (i in 0..<times.size) {
                solutions *= calc1(times[i], distanceRecords[i])
            }
            return solutions
        }

        private fun calc1(time: Int, distanceRecord: Int): Int {
            var solutions = 0
            for (speed in 0..time) {
                val distance = speed * (time - speed)
                if (distance > distanceRecord) solutions++
            }
            return solutions
        }

        fun solve2(input: List<String>): Long {
            val time =
                input[0].replace("Time:", "").replace(" ", "").toLong()
            val distanceRecord =
                input[1].replace("Distance:", "").replace(" ", "").toLong()

            return calc2(time, distanceRecord)
        }

        private fun calc2(time: Long, distanceRecord: Long): Long {
            var solutions = 0L
            for (speed in 0..time) {
                val distance = speed * (time - speed)
                if (distance > distanceRecord) solutions++
            }
            return solutions
        }
    }
}