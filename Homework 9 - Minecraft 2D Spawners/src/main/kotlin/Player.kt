import kotlin.system.exitProcess

/**
 * The player, who is controlled by key presses.
 */
class Player : LivingEntity("Steve", "Steve.png", 2000, 6) {
    var lastKeyPressed: String? = null

    /**
     * Checks whether [entity] is within fighting range of this living entity.
     * This throws [IllegalArgumentException] if either does not have a position.
     */
    private fun isInFightingRange(entity: Entity): Boolean {
        val thisPos = Game.getPosition(this)
        val entityPos = Game.getPosition(entity)
        require(thisPos != null && entityPos != null)
        return Game.calculateDistance(this, entity) < FIGHTING_RANGE
    }

    private fun attackNearbyThreats() {
        var madeAttack = false
        val entities = Game.placedEntities
        for (entity in entities) {
            if (isInFightingRange(entity) && entity is Damageable && entity !is Player) {
                attack(entity)
                madeAttack = true
            }
        }
        if (!madeAttack) {
            Game.addText("There were no nearby threats for $type to attack.")
        }
    }

    override fun tick() {
        when (lastKeyPressed) {
            "arrow-right", "s" -> moveRight()
            "arrow-left", "a" -> moveLeft()
            "arrow-up", "w" -> moveUp()
            "arrow-down", "z" -> moveDown()
            "/" -> attackNearbyThreats()
            "space", null -> {}
            else -> Game.addText("I don't know how to handle $lastKeyPressed")
        }

        // Finally, set lastKeyPressed to null
        lastKeyPressed = null
    }

    override fun exit() {
        super.exit()
        exitProcess(0)
    }
}
