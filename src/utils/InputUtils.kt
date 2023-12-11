package utils

fun List<String>.convetListOfList(delimiter: String = ""): List<List<String>> {
    return this.map { it.split("").filter { it.isNotEmpty() } }
}