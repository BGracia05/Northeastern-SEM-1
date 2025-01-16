/**
 * Something capable of taking damage.
 */
interface Damageable {
    /**
     * Takes up to [damage] hearts of damage, to a maximum of [numHearts],
     * printing a message with the amount of damage taken and the
     * resulting [status].
     */
    fun takeDamage(damage: Int)
}
