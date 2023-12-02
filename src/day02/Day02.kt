package day02

import println
import readInput

fun main() {


    fun part1(input: List<String>): Int {
        val validGames = mutableListOf<Int>()
        input.forEach { line ->
            val gameInfo = line.split(":")[0]
            val gameTurns = line.split(":")[1].split(";")
            var validGame = true
            gameTurns.forEach { gameTurn ->
                val colors = gameTurn.split(",")
                val colorPairs = colors.map {
                    Pair(Color.valueOf(it.split(" ")[2].uppercase()), it.split(" ")[1].toInt())
                }
                if (
                    colorPairs.filter { it.first == Color.RED }.sumOf { it.second } <= 12 &&
                    colorPairs.filter { it.first == Color.GREEN }.sumOf { it.second } <= 13 &&
                    colorPairs.filter { it.first == Color.BLUE }.sumOf { it.second } <= 14) {
                } else {
                    validGame = false
                }
            }
            if (validGame) {
                validGames.add(gameInfo.replace(":", "").split(" ")[1].toInt())
            }
        }
        return validGames.sum()
    }

    fun part2(input: List<String>): Int {
        val gameResult = mutableListOf<Int>()
        input.forEach { line ->
            val gameInfo = line.split(":")[0]
            val gameTurns = line.split(":")[1].split(";")
            var minRed = Int.MIN_VALUE
            var minGreen = Int.MIN_VALUE
            var minBlue = Int.MIN_VALUE
            gameTurns.forEach { gameTurn ->
                val colors = gameTurn.split(",")
                val colorPairs = colors.map {
                    Pair(Color.valueOf(it.split(" ")[2].uppercase()), it.split(" ")[1].toInt())
                }
                val sumRed = colorPairs.filter { it.first == Color.RED }.sumOf { it.second }
                if (sumRed > minRed) minRed = sumRed
                val sumGreen = colorPairs.filter { it.first == Color.GREEN }.sumOf { it.second }
                if (sumGreen > minGreen) minGreen = sumGreen
                val sumBlue = colorPairs.filter { it.first == Color.BLUE }.sumOf { it.second }
                if (sumBlue > minBlue) minBlue = sumBlue
            }
            gameResult.add(minRed * minGreen * minBlue)
        }
        return gameResult.sum()
    }

    //"Result Test 1 = ${part1(readInput("day02/Day02_1_test1"))}".println()
    //"Result Part 1 = ${part1(readInput("day02/Day02_1"))}".println()

    //"Result Test 2 = ${part2(readInput("day02/Day02_2_test1"))}".println()
    "Result Part 2 = ${part2(readInput("day02/Day02_2"))}".println()
}

private enum class Color {
    BLUE, RED, GREEN;
}