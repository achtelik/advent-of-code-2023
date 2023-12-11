package day09

import println
import readInput
import utils.convetListOfList

fun main() {
    val day = "10"
    //"Result Test 1-1 = ${Day10Solution().solve1(readInput("day$day/1_test1"))}".println()
    //"Result Test 1-2 = ${Day10Solution().solve1(readInput("day$day/1_test2"))}".println()
    //"Result Test 1-3 = ${Day10Solution().solve1(readInput("day$day/1_test3"))}".println()
    //"Result Test 1-4 = ${Day10Solution().solve1(readInput("day$day/1_test4"))}".println()
    //"Result Part 1 = ${Day10Solution().solve1(readInput("day$day/1"))}".println()

    //"Result Test 2-0= ${Day10Solution().solve2(readInput("day$day/2_test0"))}".println() // 2
    //"Result Test 2-1 = ${Day10Solution().solve2(readInput("day$day/2_test1"))}".println() // 4
    "Result Test 2-2 = ${Day10Solution().solve2(readInput("day$day/2_test2"))}".println() // 8
    //"Result Test 2-3 = ${Day10Solution().solve2(readInput("day$day/2_test3"))}".println() // 8
    //"Result Test 2-4 = ${Day10Solution().solve2(readInput("day$day/2_test4"))}".println() // 4
    //"Result Part 2 = ${Day10Solution().solve2(readInput("day$day/2"))}".println()
}

class Day10Solution {

    fun solve1(input: List<String>): Long {
        val field = input.convetListOfList()
        val startField = field.findStartField()
        val route = findRoute(field, startField)
        return (route.size / 2).toLong()
    }

    private fun List<List<String>>.findStartField(): Pair<Int, Int> {
        this.onEachIndexed { yIndex, fieldLine ->
            fieldLine.onEachIndexed { xIndex, cell ->
                if (cell == "S") return Pair(yIndex, xIndex)
            }
        }
        throw RuntimeException("Can't find start field")
    }

    val pipes = mapOf(
        "|" to listOf(Pair(1, 0), Pair(-1, 0)),
        "-" to listOf(Pair(0, 1), Pair(0, -1)),
        "L" to listOf(Pair(0, 1), Pair(-1, 0)),
        "J" to listOf(Pair(0, -1), Pair(-1, 0)),
        "7" to listOf(Pair(0, -1), Pair(1, 0)),
        "F" to listOf(Pair(0, 1), Pair(1, 0)),
    )

    private fun findRoute(field: List<List<String>>, startCell: Pair<Int, Int>): List<Pair<Int, Int>> {
        var route = mutableListOf<Pair<Int, Int>>()
        var position = startCell
        var positionNext = findStartCellNeighbour(field, position)
        route.add(position)
        route.add(positionNext)
        fillRoute(route, field)
        return route
    }

    private fun findStartCellNeighbour(field: List<List<String>>, startCell: Pair<Int, Int>): Pair<Int, Int> {
        return when {
            startCell.first - 1 >= 0 && listOf(
                "|", "7", "F"
            ).contains(field[startCell.first - 1][startCell.second]) -> Pair(
                startCell.first - 1, startCell.second
            )

            startCell.first + 1 < field.size && listOf(
                "|", "L", "J"
            ).contains(field[startCell.first + 1][startCell.second]) -> Pair(
                startCell.first + 1, startCell.second
            )

            startCell.second + 1 < field[startCell.first].size && listOf(
                "-", "7", "J"
            ).contains(field[startCell.first][startCell.second + 1]) -> Pair(
                startCell.first, startCell.second + 1
            )

            startCell.second - 1 >= 0 && listOf(
                "-", "L", "F"
            ).contains(field[startCell.first][startCell.second - 1]) -> Pair(
                startCell.first, startCell.second - 1
            )

            else -> throw RuntimeException("ERROR in findStartCellNeighbour")
        }
    }

    val possibleInsideCells = mutableSetOf<Pair<Int, Int>>()
    private fun fillRoute(route: MutableList<Pair<Int, Int>>, field: List<List<String>>) {
        var newCells = route.filter { field[it.first][it.second] != "S" }
        do {
            val possibleNewCells = newCells.flatMap { cell ->
                val offsets = pipes[field[cell.first][cell.second]]
                offsets!!.map { Pair(cell.first + it.first, cell.second + it.second) }
            }
            newCells = possibleNewCells.filter { !route.contains(it) }
            route.addAll(newCells)
        } while (newCells.isNotEmpty())
    }


    fun solve2(input: List<String>): Long {
        val field = input.convetListOfList()
        val startField = field.findStartField()
        val route = findRoute(field, startField)
        val insideCells = findInsides(route, field)
        return insideCells.size.toLong()
    }

    fun findInsides(route: List<Pair<Int, Int>>, field: List<List<String>>): List<Pair<Int, Int>> {
        var insideCells = mutableListOf<Pair<Int, Int>>()
        for (yPosition in field.indices) {
            for (xPosition in field[0].indices) {
                if (!route.contains(Pair(yPosition, xPosition))) {
                    var lastRelevantSymbol = ""
                    val intersections = mutableSetOf<Pair<Int, Int>>()
                    for (tmpXPosition in 0..xPosition) {
                        when {
                            !route.contains(Pair(yPosition, tmpXPosition - 1)) && route.contains(
                                Pair(yPosition, tmpXPosition)
                            ) -> {
                                intersections.add(Pair(yPosition, tmpXPosition))
                            }

                            route.contains(Pair(yPosition, tmpXPosition - 1)) &&
                                    !intersections.contains(Pair(yPosition, tmpXPosition - 1))
                                    && !route.contains(
                                Pair(yPosition, tmpXPosition)
                            ) -> {
                                intersections.add(Pair(yPosition, tmpXPosition))
                            }
                        }
                    }
                    if (intersections.isNotEmpty() && intersections.count() % 2 != 0) insideCells.add(
                        Pair(
                            yPosition,
                            xPosition
                        )
                    )
                }
            }
        }
        return insideCells
    }
}