import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.isDigit(): Boolean {
    if (this.length == 1) {
        return this.toCharArray()[0].isDigit()
    }
    return false
}

fun String.toIntWithWords(): Int {
    return when {
        lowercase() == "zero" -> 0
        lowercase() == "one" -> 1
        lowercase() == "two" -> 2
        lowercase() == "three" -> 3
        lowercase() == "four" -> 4
        lowercase() == "five" -> 5
        lowercase() == "six" -> 6
        lowercase() == "seven" -> 7
        lowercase() == "eight" -> 8
        lowercase() == "nine" -> 9
        else -> toInt()
    }
}
