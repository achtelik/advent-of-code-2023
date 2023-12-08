package day03

import println
import readInput
import javax.print.attribute.standard.Destination
import kotlin.math.min

fun main() {
    val day = "05"

    // "Result Test 1 = ${Day05Solution.solve1(readInput("day$day/Day${day}_1_test1"))}".println()
    // "Result Part 1 = ${Day05Solution.solve1(readInput("day$day/Day${day}_1"))}".println()

    //"Result Test 2 = ${Day05Solution.solve2(readInput("day$day/Day${day}_2_test1"))}".println()
    "Result Part 2 = ${Day05Solution.solve2(readInput("day$day/Day${day}_2"))}".println()
}

private class Day05Solution {
    companion object {
        val rangeMap = mutableMapOf<String, List<Day5Range>>()

        fun solve1(input: List<String>): Long {
            val seeds = readSeed1(input)
            return seeds.map { readDataMap(it, "seed-to-soil", input) }
                .map { readDataMap(it, "soil-to-fertilizer", input) }
                .map { readDataMap(it, "fertilizer-to-water", input) }
                .map { readDataMap(it, "water-to-light", input) }
                .map { readDataMap(it, "light-to-temperature", input) }
                .map { readDataMap(it, "temperature-to-humidity", input) }
                .map { readDataMap(it, "humidity-to-location", input) }
                .min()
        }

        fun solve2(input: List<String>): Long {
            val seedRanges = readSeedRanges(input)
            var result = Long.MAX_VALUE

            for (range in seedRanges) {
                println(range)
                for (seed in range.first..<range.first + range.second) {
                    var tmp = readDataMap(seed, "seed-to-soil", input)
                    tmp = readDataMap(tmp, "soil-to-fertilizer", input)
                    tmp = readDataMap(tmp, "fertilizer-to-water", input)
                    tmp = readDataMap(tmp, "water-to-light", input)
                    tmp = readDataMap(tmp, "light-to-temperature", input)
                    tmp = readDataMap(tmp, "temperature-to-humidity", input)
                    tmp = readDataMap(tmp, "humidity-to-location", input)
                    result = Math.min(tmp, result)
                }
            }
            return result
        }


        private fun readSeed1(input: List<String>): List<Long> {
            return input.first { it.startsWith("seeds") }
                .replace("seeds:", "").trim().split(" ")
                .map { it.trim().toLong() }
        }

        private fun readSeedRanges(input: List<String>): List<Pair<Long, Long>> {
            val seedInput = input.first { it.startsWith("seeds") }
                .replace("seeds:", "").trim().split(" ")
                .map { it.trim().toLong() }
            val ranges = mutableListOf<Pair<Long, Long>>()
            for (i in 0..<seedInput.size step 2) {
                ranges.add(Pair(seedInput[i], seedInput[i + 1]))
            }
            return ranges
        }

        private fun readDataMap(number: Long, startsWith: String, input: List<String>): Long {
            if (!rangeMap.containsKey(startsWith)) {
                var index = input.indexOfFirst { it.startsWith(startsWith) } + 1
                val ranges = mutableListOf<Day5Range>()
                while (index < input.size && input[index].isNotEmpty()) {
                    val range = input[index].split(" ").map { it.toLong() }
                    ranges.add(Day5Range(range[1], range[0], range[2]))
                    index++
                }
                rangeMap.put(startsWith, ranges)
            }
            val range =
                rangeMap[startsWith]!!.firstOrNull() { it.source <= number && it.source + it.offset - 1 >= number }
            return if (range == null) {
                number
            } else {
                number - range.source + range.destination
            }
        }
    }

    data class Day5Range(val source: Long, val destination: Long, val offset: Long)
}