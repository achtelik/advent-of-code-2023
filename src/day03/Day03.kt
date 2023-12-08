package day03

import AOCInputDataTable
import println
import readAOCInputDataTable

fun main() {
    val day = "03"

    //"Result Test 1 = ${Solution1.solve(readAOCInputDataTable("day$day/Day${day}_1_test1"))}".println()
    //"Result Part 1 = ${Solution1.solve(readAOCInputDataTable("day$day/Day${day}_1"))}".println()

    //"Result Test 2 = ${Solution2.solve(readAOCInputDataTable("day$day/Day${day}_2_test1"))}".println()
    "Result Part 2 = ${Solution2.solve(readAOCInputDataTable("day$day/Day${day}_2"))}".println()
}

private class Solution1 {
    companion object {
        fun solve(input: AOCInputDataTable): Long {
            val numbers = mutableListOf<Long>()
            var x = 0
            for (y in 0..<input.lines.size) {
                while (x < input.columns.size) {
                    if (input.lines[y][x].isDigit() && hasSymbolNeighbors(x, y, input)) {
                        val tmpResult = extractNumber(x, y, input)
                        numbers.add(tmpResult.number.toLong())
                        x += tmpResult.xOffset
                    } else {
                        x++
                    }
                }
                x = 0
            }
            return numbers.sum()
        }

        private fun hasSymbolNeighbors(x: Int, y: Int, input: AOCInputDataTable): Boolean {
            for (yOff in -1..1) {
                for (xOff in -1..1) {
                    if (y + yOff < 0 || y + yOff >= input.columns.size || x + xOff < 0 || x + xOff >= input.lines.size || (xOff == 0 && yOff == 0)) {

                    } else {
                        val c = input.lines[y + yOff][x + xOff]
                        if (!c.isDigit() && c != '.') return true
                    }
                }
            }
            return false
        }
    }
}

private class Solution2 {
    companion object {
        fun solve(input: AOCInputDataTable): Long {
            val numbers = mutableListOf<Long>()
            var x = 0
            for (y in 0..<input.lines.size) {
                while (x < input.columns.size) {
                    if (input.lines[y][x] == '*') {
                        numbers.add(getNumbers(x, y, input))
                        x++
                    } else {
                        x++
                    }
                }
                x = 0
            }
            return numbers.sum()
        }

        private fun getNumbers(x: Int, y: Int, input: AOCInputDataTable): Long {
            val result = mutableListOf<Long>()
            var xOff = -1
            for (yOff in -1..1) {
                while (xOff <= 1) {
                    if (y + yOff < 0 || y + yOff >= input.columns.size || x + xOff < 0 || x + xOff >= input.lines.size || (xOff == 0 && yOff == 0)
                        || !input.lines[y + yOff][x + xOff].isDigit()
                    ) {
                        xOff++
                    } else {
                        val tmpResult = extractNumber(x + xOff, y + yOff, input)
                        xOff += tmpResult.xOffset
                        result.add(tmpResult.number.toLong())
                    }
                }
                xOff = -1
            }
            return if (result.size == 2) result[0] * result[1]
            else 0
        }
    }
}

private fun extractNumber(x: Int, y: Int, input: AOCInputDataTable): NumberResult {
    var number = input.lines[y][x].toString()
    var xOffset = 1
    for (xPos in x - 1 downTo 0) {
        val c = input.lines[y][xPos]
        if (c.isDigit()) number = "$c$number"
        else break
    }
    for (xPos in x + 1..<input.lines[y].length) {
        xOffset++
        val c = input.lines[y][xPos]
        if (c.isDigit()) number = "$number$c"
        else break
    }
    return NumberResult(number.toInt(), xOffset)
}

private data class NumberResult(val number: Int, val xOffset: Int)

