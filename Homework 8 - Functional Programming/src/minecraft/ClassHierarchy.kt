package minecraft

const val FIGHTING_RANGE = 4.0

fun main() {
    val entities: List<Entity> =
        listOf(Zombie(), Chicken(), Sheep(), Sand(), Player())
    println(entities.map { it.type })
    val mobs: List<Mob> = entities.filterIsInstance<Mob>()
    println(mobs.map { it.type })
}

abstract class Entity(
    val type: String,
) {
    override fun toString() = type
}

abstract class Block(
    type: String,
) : Entity(type)

class Sand : Block("Sand")

class Grass : Block("Grass")

abstract class LivingEntity(
    type: String,
    val maxHearts: Int,
    val attackStrength: Int,
) : Entity(type) {
    val numHearts = maxHearts
}

class Player : LivingEntity("Steve", 20, 5)

abstract class Mob(
    type: String,
    maxHearts: Int,
    val behavior: Behavior,
    attackStrength: Int,
) : LivingEntity(type, maxHearts, attackStrength) {
    enum class Behavior {
        Passive,
        Hostile,
        Neutral,
        Boss,
    }

    val isAggressive
        get() = behavior == Behavior.Hostile || behavior == Behavior.Boss || (behavior == Behavior.Neutral && numHearts < maxHearts)
}

class Axolotl : Mob("Axolotl", 14, Behavior.Passive, 2)

class Chicken : Mob("Chicken", 10, Behavior.Passive, 0)

class Sheep : Mob("Sheep", 8, Behavior.Passive, 0)

class Skeleton : Mob("Skeleton", 15, Behavior.Hostile, 3)

class Spider : Mob("Spider", 10, Behavior.Neutral, 4)

class Witch : Mob("Witch", 20, Behavior.Hostile, 6)

class Zombie : Mob("Zombie", 20, Behavior.Hostile, 5)
