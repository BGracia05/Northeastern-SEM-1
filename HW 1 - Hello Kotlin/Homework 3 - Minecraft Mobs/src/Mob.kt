import kotlin.random.Random

/**
 * A Minecraft mob is created with its name, max number of hearts, it's behavior towards other mobs, its minimum damage(if any), and the max damage it can deal to other mobs (if any)
 * @property type the type (species)
 * @property maxHearts the maximum health level
 * @property behavior the mob's behavior (Passive, Hostile, Neutral, or Boss)
 * @property minDamage is the mob's minimum damage that it could deal
 * @property maxDamage is the mob's maximum damage that it could deal
 */
class Mob(val type: String, val maxHearts: Int, val behavior: Behavior, val minDamage: Int, val maxDamage: Int) {

    /**
     * Behavior enum class is used as a checkpoint in isAggressive to see if they are able to attack other mobs according to their Behavior value
     * @property Passive = non-aggressive mob
     * @property Hostile = aggressive mob
     * @property Boss = aggressive mob
     * @property Neutral = aggressive only when attacked first
     */
    enum class Behavior { Passive, Hostile, Neutral, Boss }

    /**
     * Status enum class is used to check if a mob is healthy, injured, or dead
     * @property Healthy = full numHearts
     * @property Injured = numHearts in 1- numHearts - 1
     * @property Dead = numHearts = 0
     */
    enum class Status { Healthy, Injured, Dead }

    var numHearts = maxHearts
        private set

    /**
     * getter method for the status of the mob after referencing its numHearts
     * @gets The current status of the mob
     */
    val status
        get() =
            when (numHearts) {
                maxHearts -> Status.Healthy
                0 -> Status.Dead
                else -> Status.Injured
            }

    /**
     * isAlive references the status of the mob to check if it is alive or not by using a getter
     * @return true if status is NOT dead, else false
     */
    val isAlive: Boolean
        get() {
            return when {
                status != Status.Dead -> true
                else -> false
            }
        }


    /**
     * isAggressive checks to make sure the mob is alive first, if so then it checks the mob's behavior and for neutral mobs if they're injured in order to check if they are aggressive.
     * @return true if mob is alive: neutral and wounded, hostile, or a boss. False for any other cases
     */
    val isAggressive: Boolean
        get() {
            return when {
                status == Status.Dead -> false
                behavior == Behavior.Hostile || behavior == Behavior.Boss || behavior == Behavior.Neutral && status != Status.Healthy -> true
                else -> false
            }
        }

    /**
     * Takes up to [damage] hearts of damage, to a maximum of [numHearts],
     * printing a message with the amount of damage taken and the
     * resulting [status].
     */
    fun takeDamage(damage: Int) {
        val actualDamage = if (damage > numHearts) numHearts else damage
        numHearts -= actualDamage
        val text = if (actualDamage == 1) "heart" else "hearts"
        println("The $type took $actualDamage $text of damage.")
        println("It is now $status.")
    }

    /**
     * As long as the attacker is aggressive, attacks [victim], doing between [minDamage] and[maxDamage]
     * (inclusive) hearts of damage.
     *
     * @throws IllegalArgumentException unless [isAggressive]
     */
    fun attack(victim: Mob) {
        if (!this.isAggressive) {
            return Unit // needed this to pass tests on doBattle() for passive mobs or healthy neutral mobs. If not present, error is thrown and rest of the tests are not ran
        }
        require(this.isAggressive || victim.isAggressive) { throw IllegalArgumentException() }
        val damage = Random.nextInt(this.minDamage, this.maxDamage + 1)
        victim.takeDamage(damage)
    }

    /**
     * Simulates a battle to the death with [opponent]. The property
     * [isAggressive] must be true for at least one of them, or neither
     * would damage the other and the fight would never end.
     *
     * @throws IllegalArgumentException if [isAggressive] is false for
     *     both this Mob and its opponent
     */
    fun battle(opponent: Mob) {
        if (!this.isAggressive && !opponent.isAggressive) {
            return Unit // needed this to pass tests on doBattle() for passive mobs or healthy neutral mobs. If not present, error is thrown and rest of the tests are not ran
        }
        require(this.isAggressive || opponent.isAggressive) { throw IllegalArgumentException() }
        while (this.isAlive && opponent.isAlive) {
            if (this.isAggressive)
                this.attack(opponent)
            if (opponent.isAlive && opponent.isAggressive)
                opponent.attack(this)
        }
        if (!this.isAlive) {
            println("$this has fallen to $opponent!")
        } else {
            println("$opponent has fallen to $this!")
        }
    }

    override fun toString() = type
}

fun main() {
    doBattle()
    runTests()
}

fun runTests() {
    testIsAlive()
    testIsAggressive()
    testAttack()
}

/**
 * checks to see if mobs in different health statuses are alive
 */
fun testIsAlive() {
    val deadMob = Mob("Dead Mob", 10, Mob.Behavior.Passive, 0, 0)
    val injuredMob = Mob("Injured Mob", 10, Mob.Behavior.Passive, 0, 0)
    val healthyMob = Mob("Healthy Mob", 10, Mob.Behavior.Passive, 0, 0)

    deadMob.takeDamage(10)
    assertFalse(deadMob.isAlive)

    injuredMob.takeDamage(5)
    assertTrue(injuredMob.isAlive)

    assertTrue(healthyMob.isAlive)

    println("All isAlive tests passed.")
}

/**
 * tests to see if a mob is aggressive when they're dead, alive, or injured(if neutral)
 */
fun testIsAggressive() {
    val hostileMob = Mob("Hostile Mob", 10, Mob.Behavior.Hostile, 2, 4)
    val bossMob = Mob("Boss Mob", 20, Mob.Behavior.Boss, 10, 20)
    val passiveMob = Mob("Passive Mob", 10, Mob.Behavior.Passive, 0, 0)
    val neutralMob = Mob("Neutral Mob", 10, Mob.Behavior.Neutral, 2, 6)

    assertTrue(hostileMob.isAggressive)
    hostileMob.takeDamage(10)
    assertFalse(hostileMob.isAggressive)

    assertTrue(bossMob.isAggressive)
    bossMob.takeDamage(20)
    assertFalse(bossMob.isAggressive)

    assertFalse(passiveMob.isAggressive)

    assertFalse(neutralMob.isAggressive)
    neutralMob.takeDamage(1)
    assertTrue(neutralMob.isAggressive)
    neutralMob.takeDamage(10)
    assertFalse(neutralMob.isAggressive)

    println("All isAggressive Tests Passed.")
}

val zom = Mob("Zombie", 30, Mob.Behavior.Hostile, 3, 5)
val skel = Mob("Skeleton", 30, Mob.Behavior.Hostile, 4, 6)
val spider = Mob("Spider", 15, Mob.Behavior.Hostile, 3, 6)
val golem = Mob("Golem", 65, Mob.Behavior.Neutral, 10, 13)
val cow = Mob("Cow", 10, Mob.Behavior.Passive, 0, 0)
val sheep = Mob("Sheep", 10, Mob.Behavior.Passive, 0, 0)
val wither = Mob("Wither", 100, Mob.Behavior.Boss, 20, 50)

/**
 * zombie attacks a skeleton and a golem, checks to see if golem is aggressive after being hit by making
 * the golem attack the zombie
 */
fun testAttack() {
    zom.attack(skel)
    zom.attack(golem)
    golem.attack(zom)
    wither.attack(golem)
}

/**
 * creates 2 mob types that are able to battle and then calls the battle method with an attacker commencing an attack on a victim.
 * zombie attacks skeleton, if skeleton is victorious, skeleton battles sheep. Next spider attacks golem, and lastly wither attacks a cow
 */
fun doBattle() {
    zom.battle(skel)
    skel.battle(sheep)
    cow.battle(sheep)
    spider.battle(golem)
    wither.battle(cow)
}