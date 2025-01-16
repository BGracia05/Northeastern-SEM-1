import ddf.minim.Minim
import kotlin.random.Random

// The per-tick probability that a mob makes noise.
const val NOISINESS = .25

/**
 * A Minecraft mob.
 */
open class Mob(
    type: String,
    maxHearts: Int,
    val behavior: Behavior,
    attackStrength: Int,
    imageFileName: String,
    soundFileName: String? = null,
    val subtitle: String? = null,
) : LivingEntity(type, imageFileName, maxHearts, attackStrength) {
    var sound = if (soundFileName == null) null else minim?.loadFile("data/sounds/$soundFileName")

    enum class Behavior { Passive, Hostile, Neutral, Boss }

    val isAggressive
        get() =
            if (!isAlive) {
                false
            } else {
                when (behavior) {
                    Behavior.Passive -> false
                    Behavior.Boss, Behavior.Hostile -> true
                    Behavior.Neutral -> status == Status.Injured
                }
            }

    /**
     * Attacks [victim], doing [attackStrength] hearts of damage. This throws
     * [IllegalArgumentException] unless [isAggressive].
     */
    override fun attack(victim: LivingEntity) {
        require(this.isAggressive)
        super.attack(victim)
    }

    fun makeNoise() {
        if (subtitle != null) {
            Game.addText(subtitle)
        }
        if (sound != null) {
            sound?.play()
            sound?.rewind()
        }
    }

    override fun tick() {
        val ranDouble = Random.nextDouble(0.0, 1.0001)
        if (ranDouble < NOISINESS) {
            makeNoise()
        }
        // If a randomly selected double in the range 0 to 1 is less than
        // NOISINESS, call makeNoise().
        if (!this.isAggressive) {
            this.moveRandomly()
        } else {
            while (Game.calculateDistance(this, Game.player.) {
                this.attack(Game.player)
            }
        }
        // If this mob is not aggressive, move in a random direction.

        // If this mob is aggressive, then either attack the player (if the
        // distance to the player is less than or equal to FIGHTING_RANGE) or
        // move towards the player.
    }


    companion object {
        var minim: Minim? = null
    }
}
