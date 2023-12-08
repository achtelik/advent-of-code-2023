package day03

import println
import readInput

fun main() {
    val day = "04"

    //"Result Test 1 = ${Day04Solution1.solve(readInput("day$day/Day${day}_1_test1"))}".println()
    //"Result Part 1 = ${Day04Solution1.solve(readInput("day$day/Day${day}_1"))}".println()

    //"Result Test 2 = ${Day04Solution2.solve(readInput("day$day/Day${day}_2_test1"))}".println()
    "Result Part 2 = ${Day04Solution2.solve(readInput("day$day/Day${day}_2"))}".println()
}

private class Day04Solution1 {
    companion object {
        fun solve(input: List<String>): Long {
            var result = 0.0
            input.map { it.split(":")[1] }
                .map {
                    val winNumbers = it.split("|")[0].split(" ").filter { it.isNotEmpty() }
                    val myNumbers = it.split("|")[1].split(" ").filter { it.isNotEmpty() }
                    val count = winNumbers.count { myNumbers.contains(it) }.toDouble() - 1
                    if (count >= 0) result += Math.pow(2.0, count)
                }
            return result.toLong()
        }
    }
}

private class Day04Solution2 {
    companion object {
        fun solve(input: List<String>): Int {
            val cardMap = mutableMapOf<Int, Int>()
            for (game in input) {
                val cardNumber = game.split(":")[0].replace("Card", "").replace(" ","").toInt()
                cardMap[cardNumber] = cardMap.getOrDefault(cardNumber, 0) + 1
                val numbers = game.split(":")[1]
                val winNumbers = numbers.split("|")[0].split(" ").filter { it.isNotEmpty() }
                val myNumbers = numbers.split("|")[1].split(" ").filter { it.isNotEmpty() }
                val count = winNumbers.count { myNumbers.contains(it) }
                for (i in 1..count) {
                    val futureCard = cardNumber + i
                    cardMap[futureCard] =
                        cardMap.getOrDefault(futureCard, 0) + (1 * cardMap.getOrDefault(cardNumber, 0))
                }
            }
            return cardMap.values.sum()
        }
    }
}