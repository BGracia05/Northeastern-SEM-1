import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.loadImage

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
    fun die() {
        Game.remove(this)
    }
}
