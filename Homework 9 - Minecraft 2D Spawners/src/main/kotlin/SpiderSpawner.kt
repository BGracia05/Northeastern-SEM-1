import kotlin.random.Random

/**
 * This class is a spider spawner in Minecraft. This entity can spawn spiders if the adjacent cells are empty
 * and have a probability of 75%
 */
class SpiderSpawner : Entity(type = "SpiderSpawner", imageFileName = "SpawnerOnSand.png"), Damageable {
    private val spawnChance: Double = 0.20

    /**
     * Checks to see if there is an adjacent empty ceel and tries to spawn a spider
     */
    private fun spawnIfSpace() {
        val pos = selectAdjacentEmptyCell()
        if (pos != null) {
            val spider = Spider()
            Game.place(spider, pos.x, pos.y)
            Game.addText("The Spider Spawner spawned a spider at ($pos.x, $pos.y)")
        } else {
            Game.addText("The Spider Spawner tried to spawn a spider but found no empty adjacent cell!")
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

    private var hardness = 6
    override fun takeDamage(damage: Int) {
        val actualDamage = if (damage > hardness) hardness else damage
        hardness -= actualDamage
        val text = if (actualDamage == 1) "heart" else "hearts"
        Game.addText("$type took $actualDamage $text of damage and is now $hardness.")
        if (hardness <= 0) {
            destroySpawner()
        }
    }

    private fun destroySpawner() {
        Game.addText("Spider Spawner has been Destroyed!")
        exit()
    }

}