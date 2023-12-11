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

abstract class Day(private val number: String, private val testResultPart1: Long?, private val testResultPart2: Long?) {
    abstract fun part1(input: List<String>): Long
    abstract fun part2(input: List<String>): Long

    fun solve() {
        // test if implementation meets criteria from the description, like:
        val testInput = readInput("Day${number}_test")
        val input = readInput("Day${number}")
        testResultPart1?.let {
            check(part1(testInput) == it)
        }
        part1(input).println()
        testResultPart2?.let {
            check(part2(testInput) == it)
        }
        part2(input).println()

    }


}
