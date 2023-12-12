package day11

import println
import readInput


fun main() {
    val day = "11"
    "Result Test 1-1 = ${Day11Solution1().solve(readInput("day$day/1_test1"))}".println()
    //"Result Part 1 = ${Day11Solution1().solve(readInput("day$day/1"))}".println()

    "Result Test 2-1 = ${Day11Solution2().solve(readInput("day$day/2_test1"))}".println() // 4
    "Result Part 2 = ${Day11Solution2().solve(readInput("day$day/2"))}".println()
}

class Day11Solution1 {

    data class GalaxyDistance(val galaxy1: Long, val galaxy2: Long, val distance: Long)
    data class Galaxy(val index: Long, val x: Long, val y: Long)

    fun solve(input: List<String>): Long {
        val updatedInput = updateInput(input)
        val galaxies = extractGalaxies(updatedInput)
        val galaxyDistances = countDistances(galaxies)
        return galaxyDistances.sumOf { it.distance }
    }

    private fun updateInput(input: List<String>): List<String> {
        var updatedInput = mutableListOf<String>()
        input.forEach {
            if (it.all {
                    it == '.'
                }) updatedInput.add(it)
            updatedInput.add(it)
        }
        var indexOffset = 0
        for (x in 0 until input[0].length) {
            if (input.all { it[x] == '.' }) {
                updatedInput =
                    updatedInput.map {
                        val stringList = it.toCharArray().toMutableList()
                        stringList.add(x + indexOffset, '.')
                        stringList.joinToString(separator = "")
                    }.toMutableList()
                indexOffset++
            }
        }
        return updatedInput
    }

    private fun extractGalaxies(input: List<String>): List<Galaxy> {
        var galaxies = mutableListOf<Galaxy>()
        var galaxyCounter = 0L
        input.forEachIndexed { index, string ->
            var tmpLine = string
            while (tmpLine.contains("#")) {
                galaxyCounter++
                galaxies.add(Galaxy(galaxyCounter, index.toLong(), tmpLine.indexOfFirst { it == '#' }.toLong()))
                tmpLine = tmpLine.replaceFirst("#", ".")
            }
        }
        return galaxies
    }

    private fun countDistances(galaxies: List<Galaxy>): List<GalaxyDistance> {
        val galaxyDistances = mutableListOf<GalaxyDistance>()
        val maxGalaxyIndex = galaxies.maxOf { it.index }
        for (index1 in 1 until maxGalaxyIndex) {
            val galaxy1 = galaxies.first { it.index == index1 }
            for (index2 in galaxy1.index + 1..maxGalaxyIndex) {
                val galaxy2 = galaxies.first { it.index == index2 }
                galaxyDistances.add(
                    GalaxyDistance(
                        galaxy1.index,
                        galaxy2.index,
                        (Math.abs(galaxy1.x - galaxy2.x) + Math.abs(galaxy1.y - galaxy2.y))
                    )
                )
            }
        }
        return galaxyDistances
    }
}

class Day11Solution2 {

    private val galaxyExpansionFactor = (1000000-1)

    data class GalaxyDistance(val galaxy1: Long, val galaxy2: Long, val distance: Long)

    data class GalaxyExpansionPoints(val x: List<Long>, val y: List<Long>)
    data class Galaxy(val index: Long, val x: Long, val y: Long)

    fun solve(input: List<String>): Long {
        val galaxies = extractGalaxies(input)
        val expansionPoints = extractGalaxyExpansionPoints(input)
        val galaxiesExpanded = updateGalaxyPosition(galaxies, expansionPoints)
        val galaxyDistances = countDistances(galaxiesExpanded)
        return galaxyDistances.sumOf { it.distance }
    }

    private fun extractGalaxyExpansionPoints(input: List<String>): GalaxyExpansionPoints {
        val expansionPointsX = mutableListOf<Long>()
        for (index in 0 until input[0].length) {
            if (input.all { it[index] == '.' }) {
                expansionPointsX.add(index.toLong())
            }
        }
        val expansionPointsY = mutableListOf<Long>()
        input.forEachIndexed { index, string ->
            if (string.all {
                    it == '.'
                }) {
                expansionPointsY.add(index.toLong())
            }
        }
        return GalaxyExpansionPoints(expansionPointsX, expansionPointsY)
    }

    private fun extractGalaxies(input: List<String>): List<Galaxy> {
        var galaxies = mutableListOf<Galaxy>()
        var galaxyCounter = 0L
        input.forEachIndexed { index, string ->
            var tmpLine = string
            while (tmpLine.contains("#")) {
                galaxyCounter++
                galaxies.add(Galaxy(galaxyCounter, tmpLine.indexOfFirst { it == '#' }.toLong(), index.toLong()))
                tmpLine = tmpLine.replaceFirst("#", ".")
            }
        }
        return galaxies
    }

    private fun updateGalaxyPosition(
        galaxies: List<Galaxy>,
        galaxyExpansionPoints: GalaxyExpansionPoints
    ): List<Galaxy> {
        return galaxies.map { galaxy ->
            val xExpansion = galaxyExpansionPoints.x.count { it < galaxy.x }
            val yExpansion = galaxyExpansionPoints.y.count { it < galaxy.y }
            Galaxy(
                galaxy.index,
                galaxy.x + (xExpansion * galaxyExpansionFactor),
                galaxy.y + (yExpansion * galaxyExpansionFactor)
            )
        }
    }

    private fun countDistances(galaxies: List<Galaxy>): List<GalaxyDistance> {
        val galaxyDistances = mutableListOf<GalaxyDistance>()
        val maxGalaxyIndex = galaxies.maxOf { it.index }
        for (index1 in 1 until maxGalaxyIndex) {
            val galaxy1 = galaxies.first { it.index == index1 }
            for (index2 in galaxy1.index + 1..maxGalaxyIndex) {
                val galaxy2 = galaxies.first { it.index == index2 }
                galaxyDistances.add(
                    GalaxyDistance(
                        galaxy1.index,
                        galaxy2.index,
                        (Math.abs(galaxy1.x - galaxy2.x) + Math.abs(galaxy1.y - galaxy2.y))
                    )
                )
            }
        }
        return galaxyDistances
    }
}