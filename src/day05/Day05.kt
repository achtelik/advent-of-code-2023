package day03

import println
import readInput
import kotlin.math.min

fun main() {
    val day = "05"

    //"Result Test 1 = ${Day05Solution.solve1(readInput("day$day/Day${day}_1_test1"))}".println()
    // "Result Part 1 = ${Day05Solution.solve1(readInput("day$day/Day${day}_1"))}".println()

    //"Result Test 2 = ${Day05Solution.solve2(readInput("day$day/Day${day}_2_test1"))}".println()
    "Result Part 2 = ${Day05Solution.solve2(readInput("day$day/Day${day}_2"))}".println()
}

private class Day05Solution {
    companion object {
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

            for (range in seedRanges.subList(0, 1)) {
                for (seed in range.first..<range.first + range.second) {
                    val tmpResult = readDataMap(seed, "seed-to-soil", input)
                    result = min(tmpResult, result)
                }

                //.map { readDataMap(it, "seed-to-soil", input) }
                //.map { readDataMap(it, "soil-to-fertilizer", input) }
                //.map { readDataMap(it, "fertilizer-to-water", input) }
                //.map { readDataMap(it, "water-to-light", input) }
                //.map { readDataMap(it, "light-to-temperature", input) }
                //.map { readDataMap(it, "temperature-to-humidity", input) }
                //.map { readDataMap(it, "humidity-to-location", input) }

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
            var index = input.indexOfFirst { it.startsWith(startsWith) } + 1
            while (index < input.size && input[index].isNotEmpty()) {
                val range = input[index].split(" ").map { it.toLong() }
                if (range[1] <= number && range[1] + range[2] - 1 >= number) {
                    return number - range[1] + range[0]
                }
                index++
            }
            return number
        }
    }
}