import kotlin.random.Random

/**
 * Rolls a single six-sided die.
 *
 * @return a random number in the range 1-6
 */
fun rollDie(): Int {
    // The second argument to nextInt() is the upper bound, which is exclusive,
    // so this will return a number in the range 1-6.
    return Random.nextInt(1, 7)
}

/**
 * Rolls 3 six-sided dice.
 *
 * @return the sum of 3 random numbers in the range 1-6
 * BUG: rolls one die and multiplies it by 3 leading to only factors of 3
 * FIX: I decided to make a repeating loop that spins the die once, stores the value, and spins 2 more times totaling 3 spins. totalPoints are then returned
 */
fun rollThreeDice(): Int {
    var totalPoints = 0
    for (i in 0 until 3) {
        var dieVal = rollDie()
        totalPoints += dieVal
        dieVal = 0
    }
    return totalPoints
}

/**
 * Rolls a single die having numbers in the range [minValue] to [maxValue]
 * (inclusive).
 *
 * @return a random number in the range [minValue] to [maxValue] (inclusive)
 */
fun rollBiasedDie(minValue: Int, maxValue: Int): Int {
    return Random.nextInt(minValue, maxValue + 1)
}

/**
 * Rolls 3 dice having numbers in the range [minValue] to [maxValue] (inclusive).
 *
 * @return the sum of 3 random numbers in the range [minValue] to [maxValue]
 */
fun rollThreeBiasedDice(minValue: Int, maxValue: Int): Int {
    var totalPoints = 0
    for (i in 0 until 3) {
        var dieVal = rollBiasedDie(minValue, maxValue)
        totalPoints += dieVal
        dieVal = 0
    }
    return totalPoints
}

/**
 * Makes a character with a user-provided name and randomly-selected stats
 * using 3 ordinary six-sided dice.
 *
 * @return a sentence describing the character
 */
fun makeCharacter(): String {
    println("Enter your character's name:")
    val name = readln()
    val charisma = rollThreeDice()
    val agility = rollThreeDice()
    val speed = rollThreeDice()
    return "$name has charisma $charisma, agility $agility, and speed $speed."
}

/**
 * Makes a potentially superior character with a user-provided name and
 * randomly-selected stats using 3 positively biased dice.
 *
 * @return a sentence describing the hero
 */
fun makeHero(): String {
    println("Enter your hero's name:")
    val name = readln()
    val charisma = rollThreeBiasedDice(3, 6)
    val agility = rollThreeBiasedDice(3, 6)
    val speed = rollThreeBiasedDice(3, 6)
    return "The hero $name has charisma $charisma, agility $agility, and speed $speed."
}

/**
 * Makes a character with a user-provided name and randomly-selected stats
 * using 3 dice whose minimum and maximum values are specified by the user.
 *
 * @return a sentence describing the adventurer
 */
fun makeAdventurer(): String {
    println("Enter your adventurers name:")
    val name = readln()

    println("Input the minimum value of your die")
    val minDie = Integer.valueOf(readln())

    println("Input the maximum value of your die")
    val maxDie = Integer.valueOf(readln())
    
    val charisma = rollThreeBiasedDice(minDie, maxDie)
    val agility = rollThreeBiasedDice(minDie, maxDie)
    val speed = rollThreeBiasedDice(minDie, maxDie)
    return "The adventurer $name has charisma $charisma, agility $agility, and speed $speed."
}
