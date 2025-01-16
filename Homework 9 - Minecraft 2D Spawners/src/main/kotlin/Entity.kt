import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.loadImage
import kotlin.random.Random

/**
 * An animate or inanimate object in the game.
 *
 * @constructor Constructs an entity with the given [type] and [imageFileName].
 */
abstract class Entity(
    val type: String,
    imageFileName: String,
) {
    val image: ColorBuffer = loadImage("data/images/$imageFileName")

    /**
     * Takes an action during its turn.
     */
    abstract fun tick()

    /**
     * Removes this entity from the game. It will no longer appear
     * on the screen, and its [tick] method will no longer be called.
     */
    open fun exit() {
        Game.remove(this)
    }

    /**
     * Returns the position of an empty cell adjacent to this entity or
     * `null` if no such cell exists or if this entity is not on the board.
     */
    fun selectAdjacentEmptyCell(): Position? {
        val currentPos = Game.getPosition(this) ?: return null

        val adjacentPos = listOf(
            Position(currentPos.x - 1, currentPos.y),
            Position(currentPos.x + 1, currentPos.y),
            Position(currentPos.x, currentPos.y - 1),
            Position(currentPos.x, currentPos.y + 1)
        )
        for (position in adjacentPos) {
            if (Game.isInBounds(position.x, position.y) && Game.isEmpty(position.x, position.y)) {
                return position
            }
        }
        return null
    }

    override fun toString() = type

}






