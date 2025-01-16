package minecraft

import java.lang.Math.pow

const val NUM_COLS = 10
const val NUM_ROWS = 10

/**
 * A singleton (unique) object encapsulating the game state.
 */
object Game {
    private val blockGrid =
        Array(NUM_COLS) { col ->
            Array(NUM_ROWS) { row ->
                // Create checkerboard pattern of Grass and Sand
                if ((col - row) % 2 == 0) Grass() else Sand()
            }
        }

    private val entityGrid =
        Array(NUM_COLS) {
            Array<Entity?>(NUM_ROWS) {
                null
            }
        }

    /**
     * The player of the game.
     */
    val player = Player()

    private val positions = mutableMapOf<Entity, Position>()

    /**
     * All entities that have been placed on the grid.
     */
    val placedEntities
        get() = positions.keys.toList()

    init {
        place(Witch(), 1, 1) // sqrt(32)
        place(Zombie(), 2, 4) // sqrt(10)
        place(Sheep(), 3, 3) // sqrt(4)
        place(Witch(), 4, 6) // sqrt(2)
        place(player, 5, 5)
        place(Skeleton(), 6, 1) // sqrt(17)
        place(Spider(), 7, 2) // sqrt(13)
    }

    /**
     * Tests whether the given [x] and [y] coordinates are in bounds.
     */
    private fun isInBounds(
        x: Int,
        y: Int,
    ) = x in 0.until(NUM_COLS) && y in 0.until(NUM_ROWS)

    /**
     * Returns whether the cell with the specified [x] and [y] coordinates is
     * occupied by an [Entity] in the game. It is illegal to try to move
     * another [Entity] into an occupied cell. This returns `false` if
     * the coordinates are out of bounds.
     */
    private fun isOccupied(
        x: Int,
        y: Int,
    ) = isInBounds(x, y) && entityGrid[x][y] != null

    /**
     * Returns whether the cell with the specified [x] and [y] coordinates is
     * unoccupied. See [isOccupied]. This returns `false` if the coordinates
     * are out of bounds.
     */
    private fun isEmpty(
        x: Int,
        y: Int,
    ) = isInBounds(x, y) && !isOccupied(x, y)

    /**
     * Places the [entity] at the specified [x] and [y] coordinates, throwing [IllegalArgumentException]
     * if that position [isOccupied] or out of bounds.
     */
    fun place(
        entity: Entity,
        x: Int,
        y: Int,
    ) {
        require(isEmpty(x, y)) {
            "You cannot place the $entity at ($x, $y), which is already occupied by a ${
                getEntity(
                    x,
                    y,
                )
            }."
        }
        require(isInBounds(x, y)) {
            "The location ($x, $y) is out of bounds."
        }
        if (entity in positions) {
            remove(entity)
        }
        entityGrid[x][y] = entity
        positions[entity] = Position(x, y)
    }

    /**
     * Removes the [entity] from the game, so it no longer appears on
     * the grid and its [Entity.tick] method is no longer called.
     * This throws [IllegalArgumentException] if had not been on the grid.
     */
    fun remove(entity: Entity) {
        require(entity in positions) {
            "The $entity was not on the grid."
        }
        val position = positions[entity]
        if (position == null) {
            throw java.lang.IllegalArgumentException("The $entity was not on the grid.")
        }
        entityGrid[position.x][position.y] = null
        positions.remove(entity)
        if (entity is Player) System.exit(0)
    }

    /**
     * Gets whatever entity is at the specified [x] and [y] coordinates,
     * or `null` if it is unoccupied or the position is out of bounds.
     */
    fun getEntity(
        x: Int,
        y: Int,
    ): Entity? =
        if (x in 0..NUM_COLS && y in 0..NUM_ROWS) {
            entityGrid[x][y]
        } else {
            null
        }

    /**
     * Gets whatever block is at the specified [x] and [y] coordinates
     * or `null` if the position out of bounds.
     */
    fun getBlock(
        x: Int,
        y: Int,
    ): Block? =
        if (isInBounds(x, y)) {
            blockGrid[x][y]
        } else {
            null
        }

    /**
     * Calculates the Euclidean distance between [entity1] and [entity2],
     * throwing [IllegalArgumentException] if either has not been placed.
     */
    fun calculateDistance(
        entity1: Entity,
        entity2: Entity,
    ): Double {
        val pos1 = getPosition(entity1)
        val pos2 = getPosition(entity2)
        val dx = pos1.x - pos2.x
        val dy = pos1.y - pos2.y
        return Math.sqrt(pow(dx.toDouble(), 2.0) + pow(dy.toDouble(), 2.0))
    }

    /**
     * Returns the position of the given [entity], or `null` if it has
     * not been placed.
     */
    fun getPosition(entity: Entity): Position =
        positions[entity]
            ?: throw java.lang.IllegalStateException("$entity not on board")
}
