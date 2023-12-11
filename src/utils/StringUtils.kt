package utils

fun String.splitToLong(delimiter: String = " "): List<Long> {
    return this.split(delimiter).filter(String::isNotEmpty).map(String::toLong)
}