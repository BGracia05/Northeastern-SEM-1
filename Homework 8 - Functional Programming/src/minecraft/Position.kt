package minecraft

/**
 * An immutable pair of [x]-[y] coordinates.
 */
data class Position(
    val x: Int,
    val y: Int,
) {
    override fun toString() = "($x, $y)"
}
