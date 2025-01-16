const val FIGHTING_RANGE = 3.0

/**
 * A living entity, such as a [Mob] or [Player] in the game.
 */
abstract class LivingEntity(
    type: String,
    imageFileName: String,
    val maxHearts: Int,
    val attackStrength: Int,
) : Entity(type, imageFileName) {
    enum class Status { Healthy, Injured, Dead }

    var numHearts = maxHearts
        private set

    var status = Status.Healthy
        private set
        get() =
            when (numHearts) {
                maxHearts -> Status.Healthy
                0 -> Status.Dead
                else -> Status.Injured
            }

    val isAlive
        get() = status != Status.Dead

    override fun toString() = "$status $type"

    /**
     * Takes up to [damage] hearts of damage, to a maximum of [numHearts],
     * printing a message with the amount of damage taken and the
     * resulting [status].
     */
    fun takeDamage(
        attacker: LivingEntity,
        damage: Int,
    ) {
        Game.addText("${attacker.type} attacked $type.")
        val actualDamage = if (damage > numHearts) numHearts else damage
        numHearts -= actualDamage
        val text = if (actualDamage == 1) "heart" else "hearts"
        Game.addText("$type took $actualDamage $text of damage and is now $status.")
        if (status == Status.Dead) {
            this.die()
        }
    }

    /**
     * Attacks [victim], doing [attackStrength] hearts of damage.
     */
    open fun attack(victim: LivingEntity) {
        Game.addText("$type attacked ${victim.type}.")
        victim.takeDamage(this, attackStrength)
    }

    /**
     * Moves right one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds. This should throw
     * [IllegalArgumentException] if this living entity is not on the board.
     */
    fun moveRight() {
        var curPosition = Game.getPosition(this)
        while(//keyinput == "d") {
            Game.place(Game.player, + 1, 0)
    }
        // You should call Game.getPosition() and Game.place() as part of
        // your implementation.
    }

    /**
     * Moves left one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveLeft() {
    }

    /**
     * Moves up one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveUp() {
        // On the game board, moving up decreases the y-coordinate.
    }

    /**
     * Moves down one cell, or stays in the same place, if that cell is
     * occupied by a [LivingEntity] or out of bounds.
     */
    fun moveDown() {
        // On the game board, moving down increases the y-coordinate.
    }

    /**
     * Moves randomly to an adjacent unoccupied cell. Cells
     * are considered adjacent if they are to the right, left, up,
     * or down (i.e., changing by 1 either the x-coordinate or the
     * y-coordinate but not both).
     */
    fun moveRandomly() {
    }

    /**
     * Tries to move to an adjacent cell closer to [player]. Cells
     * are considered adjacent if they are to the right, left, up,
     * or down (i.e., changing by 1 either the x-coordinate or the
     * y-coordinate but not both). If all closer adjacent cells are
     * occupied, no movement will occur. This should throw
     * [IllegalArgumentException] if this living entity or [player]
     * is not on the board.
     */
    fun moveTowards(player: Player) {
    }
}
