
// Do not modify this code.

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