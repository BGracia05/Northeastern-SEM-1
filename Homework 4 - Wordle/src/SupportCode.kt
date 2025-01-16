import java.io.File

/**
 * Verifies that [actual] is equal to [expected].
 *
 * @throws AssertionError if they are not equal
 */
fun assertEquals(
    expected: Any,
    actual: Any,
) {
    if (expected != actual) {
        throw AssertionError("Expected $expected, got $actual")
    }
}

/**
 * Verifies that [actual] is true.
 *
 * @throws AssertionError if it is false
 */
fun assertTrue(actual: Any) {
    assertEquals(true, actual)
}

/**
 * Verifies that [actual] is false.
 *
 * @throws AssertionError if it is true
 */
fun assertFalse(actual: Any) {
    assertEquals(false, actual)
}

/**
 * Reads words of length [wordLength] from a file, returning a list of the words.
 */
fun readWordsFromFile(wordLength: Int): List<String> = File("src/words$wordLength.txt").readLines()

/**
 * Keeps prompting the user for a valid integer into receiving one, which it returns.
 */
fun readInt(): Int {
    var result: Int? = null
    while (result == null) {
        print("Enter a valid integer: ")
        result = readlnOrNull()?.toIntOrNull()
    }
    return result
}
