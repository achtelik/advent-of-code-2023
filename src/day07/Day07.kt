package day03

import day08.Day08Solution
import println
import readInput

fun main() {
    val day = "07"
    //"Result Test 1 = ${Day07Solution.solve1(readInput("day$day/1_test1"))}".println()
    //"Result Test 2 = ${Day07Solution.solve1(readInput("day$day/1_test2"))}".println()
    //"Result Part 1 = ${Day07Solution.solve1(readInput("day$day/1"))}".println()

    //"Result Test 2 = ${Day07Solution.solve2(readInput("day$day/2_test1"))}".println()
    //"Result Test 2 = ${Day07Solution.solve2(readInput("day$day/2_test2"))}".println()
    "Result Part 2 = ${Day07Solution.solve2(readInput("day$day/2"))}".println()
}

class Day07Solution {
    companion object {
        fun solve1(input: List<String>): Long {
            val data = input.map { Day07Data1(it.split(" ")[0], it.split(" ")[1].toLong()) }.sorted()
            var result = 0L
            data.forEachIndexed { index, tmpData -> result += (index + 1) * tmpData.bid }
            return result
        }

        fun solve2(input: List<String>): Long {
            val data = input.map { Day07Data2(it.split(" ")[0], it.split(" ")[1].toLong()) }.sorted()
            var result = 0L
            data.forEachIndexed { index, tmpData -> result += (index + 1) * tmpData.bid }
            return result
        }
    }

    data class Day07Data1(var hand: String, val bid: Long, var value: Long = Long.MIN_VALUE) : Comparable<Day07Data1> {
        constructor(hand: String, bid: Long) : this(
            mapHand(hand),
            bid,
            calculateValue(mapHand(hand))
        )

        override fun compareTo(other: Day07Data1): Int {
            return when {
                this.value != other.value -> this.value.compareTo(other.value)
                else -> this.hand.compareTo(other.hand)
            }
        }

        companion object {
            fun mapHand(hand: String): String {
                return hand.replace("A", "M")
                    .replace("K", "L")
                    .replace("Q", "K")
                    .replace("T", "I")
                    .replace("9", "H")
                    .replace("8", "G")
                    .replace("7", "F")
                    .replace("6", "E")
                    .replace("5", "D")
                    .replace("4", "C")
                    .replace("3", "B")
                    .replace("2", "A")
            }

            fun calculateValue(hand: String): Long {
                val resultMap = mutableMapOf<Char, Int>()
                for (c in hand) resultMap[c] = resultMap.getOrDefault(c, 0) + 1
                return when {
                    resultMap.values.contains(5) -> 6
                    resultMap.values.contains(4) -> 5
                    resultMap.values.contains(3) && resultMap.values.contains(2) -> 4
                    resultMap.values.contains(3) -> 3
                    resultMap.values.count { it == 2 } == 2 -> 2
                    resultMap.values.count { it == 2 } == 1 -> 1
                    resultMap.values.max() == 1 -> 0
                    else -> throw IllegalArgumentException("ERROR")
                }
            }
        }
    }

    data class Day07Data2(var hand: String, val bid: Long, var value: Long = Long.MIN_VALUE) : Comparable<Day07Data2> {
        constructor(hand: String, bid: Long) : this(
            mapHand(hand),
            bid,
            calculateValue(mapHand(hand))
        )

        override fun compareTo(other: Day07Data2): Int {
            return when {
                this.value != other.value -> this.value.compareTo(other.value)
                else -> this.hand.compareTo(other.hand)
            }
        }

        companion object {
            fun mapHand(hand: String): String {
                return hand.replace("A", "N")
                    .replace("K", "M")
                    .replace("Q", "L")
                    .replace("T", "K")
                    .replace("9", "I")
                    .replace("8", "H")
                    .replace("7", "G")
                    .replace("6", "F")
                    .replace("5", "E")
                    .replace("4", "D")
                    .replace("3", "C")
                    .replace("2", "B")
                    .replace("J", "A")
            }

            fun calculateValue(hand: String): Long {
                var bestResult = Long.MIN_VALUE
                for (c in "BCDEFGHIKLMN") {
                    val resultMap = mutableMapOf<Char, Int>()
                    val handWithReplacedJokes = hand.replace("A", c.toString())
                    for (c in handWithReplacedJokes) resultMap[c] = resultMap.getOrDefault(c, 0) + 1
                    bestResult = Math.max(bestResult, when {
                        resultMap.values.contains(5) -> 6
                        resultMap.values.contains(4) -> 5
                        resultMap.values.contains(3) && resultMap.values.contains(2) -> 4
                        resultMap.values.contains(3) -> 3
                        resultMap.values.count { it == 2 } == 2 -> 2
                        resultMap.values.count { it == 2 } == 1 -> 1
                        resultMap.values.max() == 1 -> 0
                        else -> throw IllegalArgumentException("ERROR")
                    })
                    if (bestResult == 6L) break
                }
                return bestResult
            }
        }
    }
}