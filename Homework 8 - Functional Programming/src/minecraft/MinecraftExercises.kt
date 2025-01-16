package minecraft

import assertEquals
import assertFalse
import assertTrue

fun main() {
    testCountNeutralMobs()
    testGetNumEntitiesPrintedAs()
    testIsBehaviorPresent()
    testGetLivingEntityTypesSortedByX()
    testIsPlayerStrongest()
    testGetStrengthsOfNearbyThreats()
    testGetLocationList()
    testGetBlockNamesBeneathPlacedEntities()
    testGetDescriptionsOfEntitiesAndBlocks()
    testCheckIfCanSleepWithoutHostiles()
    testCheckIfCanSleepWithHostiles()
    println("All tests passed.")
}

/**
 * Gets a count of the number of neutral mobs on the board.
 */
fun countNeutralMobs(): Int =
    Game.placedEntities.filterIsInstance<Mob>().count { mob -> mob.behavior == Mob.Behavior.Neutral }

fun testCountNeutralMobs() {
    assertEquals(1, countNeutralMobs())
}

/**
 * Gets the number of entities whose string representation is [printedForm].
 */
fun getNumEntitiesPrintedAs(printedForm: String): Int =
    Game.placedEntities.count { entity -> entity.toString() == printedForm }

fun testGetNumEntitiesPrintedAs() {
    assertEquals(1, getNumEntitiesPrintedAs("Zombie"))
    assertEquals(2, getNumEntitiesPrintedAs("Witch"))
    assertEquals(0, getNumEntitiesPrintedAs("Axolotl"))
}

/**
 * Returns whether any mobs have the specified [behavior].
 */
fun isBehaviorPresent(behavior: Mob.Behavior): Boolean =
    Game.placedEntities.filterIsInstance<Mob>().any { mob -> mob.behavior == behavior }

fun testIsBehaviorPresent() {
    assertTrue(isBehaviorPresent(Mob.Behavior.Passive))
    assertFalse(isBehaviorPresent(Mob.Behavior.Boss))
}

/**
 * Gets the type of every [Mob] sorted by increasing x-coordinate.
 */
fun getLivingEntityTypesSortedByX(): List<String> =
    Game.placedEntities.filter { it is Mob }.sortedBy { Game.getPosition(it).x }.map { it::class.simpleName.orEmpty() }

fun testGetLivingEntityTypesSortedByX() {
    val expected = listOf("Witch", "Zombie", "Sheep", "Witch", "Skeleton", "Spider")
    val actual = getLivingEntityTypesSortedByX()
    assertEquals(expected, actual)
}

/**
 * Returns true if [Game.player] has higher [LivingEntity.numHearts] than any
 * other living entity.
 */
fun isPlayerStrongest() =
    // Hint: Try to rephrase the requirement to use the word "any" or "all".
    Game.placedEntities.filterIsInstance<LivingEntity>().filter { it != Game.player }
        .all { it.numHearts < Game.player.numHearts }

fun testIsPlayerStrongest() {
    assertFalse(isPlayerStrongest())
}

/**
 * Gets the attack strength of every aggressive and mob whose distance from
 * [Game.player] is within fighting range. The list is sorted by increasing
 * distance.
 */
fun getStrengthsOfNearbyThreats(): List<Int> {
    return Game.placedEntities.filterIsInstance<Mob>()
        .filter { it.isAggressive && Game.calculateDistance(it, Game.player) <= FIGHTING_RANGE }
        .sortedBy { Game.calculateDistance(it, Game.player) }.map { it.attackStrength }
}


fun testGetStrengthsOfNearbyThreats() {
    val expected = listOf(6, 5)
    val actual = getStrengthsOfNearbyThreats()
    assertEquals(expected, actual)
}

/**
 * Returns a list giving the type and location of each living entity on the
 * board, sorted from top to bottom of the board.
 */
fun getLocationList(): List<String> =
    Game.placedEntities.filterIsInstance<LivingEntity>().sortedBy { Game.getPosition(it).y }
        .map { "${it::class.simpleName} at (${Game.getPosition(it).x}, ${Game.getPosition(it).y})" }

fun testGetLocationList() {
    val actual = getLocationList()
    val expected =
        listOf(
            "Witch at (1, 1)",
            "Skeleton at (6, 1)",
            "Spider at (7, 2)",
            "Sheep at (3, 3)",
            "Zombie at (2, 4)",
            "Player at (5, 5)",
            "Witch at (4, 6)",
        )
    assertEquals(expected, actual)
}

/**
 * Gets the string representations of the blocks on which entities are placed.
 * Each string representation should occur no more than once.
 */
fun getBlockNamesBeneathPlacedEntities(): List<String> =
    Game.placedEntities.map { Game.getBlock(Game.getPosition(it).x, Game.getPosition(it).y)?.toString() }
        .filterNotNull().distinct()
// You will need to use Game.getBlock() to get the blocks at each entity's
// position.
//
// Game.getBlock() will never return null for placed entities because they
// are in bounds, but do not use "!!". Find another way to ensure the output
// is List<String> (and not List<String?>).
//
// To get a string representation of an object, call its toString() method.


fun testGetBlockNamesBeneathPlacedEntities() {
    val actual = getBlockNamesBeneathPlacedEntities()
    assertEquals(2, actual.size)
    // The strings can be in any order.
    assertTrue(actual.contains("Grass"))
    assertTrue(actual.contains("Sand"))
}

/**
 * Returns an alphabetical list of statements about each entity and the block it
 * is placed on, such as "Axolotl is on Sand at (3, 4)", which would precede
 * "Zombie is on Sand at (2, 2)"
 */
fun getDescriptionsOfEntitiesAndBlocks(): List<String> {
    fun entityAndBlock(entity: Entity): String {
        val pos = Game.getPosition(entity)
        val block = Game.getBlock(pos.x, pos.y)

        return "$entity is on ${block.toString()} at (${(pos.x)}, ${(pos.y)})"
    }
    return Game.placedEntities.filterIsInstance<LivingEntity>().sortedBy { it.type }.map { entityAndBlock(it) }
}


fun testGetDescriptionsOfEntitiesAndBlocks() {
    val expected =
        listOf(
            "Sheep is on Grass at (3, 3)",
            "Skeleton is on Sand at (6, 1)",
            "Spider is on Sand at (7, 2)",
            "Steve is on Grass at (5, 5)",
            "Witch is on Grass at (1, 1)",
            "Witch is on Grass at (4, 6)",
            "Zombie is on Grass at (2, 4)",
        )
    val actual = getDescriptionsOfEntitiesAndBlocks()
    assertEquals(expected, actual)
}

/**
 * Checks to see if there are any hostile entities within the range of [player] to see if they can sleep
 * if there are hostile mob within 10 blocks, player cant sleep and returns "You can't sleep, there are monsters nearby!"
 *
 * @return message indicating whether you are sleeping or if you can't
 */

fun checkIfCanSleep(): String {
    val sleepRange = 10
    val hostileEntities = Game.placedEntities.filterIsInstance<Mob>()
        .filter { it.behavior == Mob.Behavior.Hostile }
        .filter { Game.calculateDistance(Game.player, it) <= sleepRange }

    return if (hostileEntities.isNotEmpty()) {
        "You can't sleep, there are monsters nearby!"
    } else {
        "ZZZ"
    }
}

// I had to make place not private for this
fun testCheckIfCanSleepWithHostiles() {
    val hostileMob = Zombie()
    Game.place(hostileMob, 5, 6)

    val result = checkIfCanSleep()
    assertEquals("You can't sleep, there are monsters nearby!", result)

    // Clean up by removing the hostile mob after the test
    Game.remove(hostileMob)
}

fun testCheckIfCanSleepWithoutHostiles() {
    Game.placedEntities.filterIsInstance<Mob>()
        .filter { it.behavior == Mob.Behavior.Hostile }
        .forEach { Game.remove(it) }

    val result = checkIfCanSleep()
    assertEquals("ZZZ", result)
}

