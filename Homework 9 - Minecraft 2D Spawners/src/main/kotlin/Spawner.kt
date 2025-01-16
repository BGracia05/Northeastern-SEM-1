import kotlin.random.Random

/**
 * A spawner of any type of [Entity].
 */
open class Spawner<T : Entity>(
    private val spawnType: () -> T,
    private val spawnProbability: Double,
    private var hardness: Int
) :
    Entity("Spawner", "SpawnerOnSand.png"), Damageable {

    private var numHearts = hardness
    private val spawnChance = 0.25


    private fun spawn(): T {
        return spawnType()
    }

    /**
     * Spawns a new entity type [T] if there is an empty adjacent place
     */
    private fun spawnIfSpace() {
        val pos = selectAdjacentEmptyCell()
        if (pos != null) {
            val spider = Spider()
            val newEntity = spawn()
            Game.place(newEntity, pos.x, pos.y)
            Game.addText("A ${newEntity::class.simpleName} spawned at ($pos.x, $pos.y)")
        } else {
            Game.addText("Spawner found no empty adjacent cell!")
        }
    }

    override fun tick() {
        var randomNumber = Random.nextDouble()
        if (randomNumber < spawnChance) {
            Game.addText("The RNG is : $randomNumber, successfully spawned Spider!")
            spawnIfSpace()
        } else {
            Game.addText("The RNG is : $randomNumber, unsuccessfully spawned Spider!")
        }
    }

    override fun takeDamage(damage: Int) {
        val actualDamage = if (damage > hardness) hardness else damage
        hardness -= actualDamage
        val text = if (actualDamage == 1) "point of damage" else "points of damage"
        Game.addText("Spawner took $actualDamage $text and now has $hardness remaining hardness.")
        if (hardness <= 0) {
            destroySpawner()
        }
    }

    private fun destroySpawner() {
        Game.addText("The Spawner has been Destroyed!")
        exit()
    }
}