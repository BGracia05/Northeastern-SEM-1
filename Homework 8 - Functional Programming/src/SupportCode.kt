/**
 * Verifies that [actual] is equal to [expected], throwing [AssertionError] if
 * they are not.
 */
fun assertEquals(expected: Any, actual: Any) {
    if (expected != actual) {
        throw AssertionError("Expected $expected, got $actual")
    }
}

/**
 * Verifies that [actual] is `true`, throwing [AssertionError] if it is not.
 */
fun assertTrue(actual: Boolean) {
    if (!actual) {
        throw AssertionError("Expected true, got false")
    }
}

/**
 * Verifies that [actual] is `false`, throwing [AssertionError] if it is not.
 */
fun assertFalse(actual: Boolean) {
    if (actual) {
        throw AssertionError("Expected false, got true")
    }
}

/**
 * Verifies that the lists [expected] and [actual] have the same size and
 * contents.
 */
fun assertEquals(expected: List<Any>, actual: List<Any>) {
    assertEquals(expected.size, actual.size)
    for (i in expected.indices) {
        assertEquals(expected[i], actual[i])
    }
}

/**
 * Verifies that an exception of time [t] was thrown by [block].
 */
inline fun <reified T : Throwable> assertThrows(block: () -> Unit) {
    try {
        block()
        throw AssertionError("Expected ${T::class.simpleName} to be thrown, but nothing was thrown.")
    } catch (e: Throwable) {
        if (e !is T) {
            throw AssertionError("Expected ${T::class.simpleName} to be thrown, but ${e::class.simpleName} was thrown instead.")
        }
    }
}