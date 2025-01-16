/**
 * A single Wordle game.
 *
 * @property secretWord the word for the user to guess
 */
class WordleGame(val secretWord: String) {
    /**
     * @property guesses stores any guess inputted into a mutable list
     */
    val guesses: MutableList<String> = mutableListOf()

    /**
     * @gets the number of guesses that have been done by the user
     */
    val numGuesses: Int
        get() = guesses.size

    /**
     * @gets true if the guess inputted is equal to the secretWord, else not satisfied
     */
    val wordFound: Boolean
        get() = when {
            guesses.lastOrNull() == secretWord -> true
            else -> false
        }


    /**
     * Guesses that the secret word is [guess]. This returns a feedback String.
     * For every position where [guess] matches [secretWord], a star (*)
     * appears. For positions where the letter in [guess] corresponds to a
     * different position in [secretWord], a plus sign (+) appears. If there
     * is no match, a dot (.) appears. If guess has already been inputted, return feedback only
     *
     * @throws IllegalArgumentException if the length of [guess] is not the same
     * as the length of [secretWord]
     * @property feedback is the string returned that checks what matches and what doesn't
     */
    fun makeGuess(guess: String): String {
        if (guess.length != secretWord.length) {
            throw IllegalArgumentException("$guess is not the same length as the secret word!")
        }
        val feedback = StringBuilder(".".repeat(secretWord.length))

        for (i in guess.indices) {
            if (guess[i].uppercaseChar() == secretWord[i]) {
                feedback[i] = '*'
            }
        }
        for (i in guess.indices) {
            if (feedback[i] == '.' && secretWord.contains(guess[i].uppercaseChar())) {
                feedback[i] = '+'
            }
        }
        if (guesses.contains(guess)) {
            println("Word already used, you will not be penalized")
        } else {
            guesses.add(guess.uppercase())
        }
        return feedback.toString()
    }
}

/**
 * Plays Wordle while playAgain is true. refers to the makeGuess method to check when guess is = to secretWord or not. Reports the number of guesses and the guess list after success
 * and then prompts the user if he wants to play again, if user inputs "no", program ends
 *
 * @property wordLength the length of the secret word
 * @property playAgain is true whenever the game is asked to replay, starts off true
 * @property words takes the word.txt with the inputted wordlength parameter
 * @property secretWord randomly stores a word from the word.txt
 * @property game starts the WordleGame with the random secretWord as a parameter
 * @property inputGuess is the user's inputted guess
 * @property resultString runs the makeGuess method and stores the feedback
 * @property response stores the user input when asked if they want to play again
 */
fun playGame(wordLength: Int) {
    var playAgain = true
    val words = readWordsFromFile(wordLength)
    do {
        val secretWord = words.random()
        val game = WordleGame(secretWord)

        while (!game.wordFound) {
            println("Enter a valid guess:")
            val inputGuess = readln()
            val resultString = game.makeGuess(inputGuess)
            println(resultString)
        }
        println("Nice! You guessed the word ${game.secretWord} in ${game.numGuesses} tries!")
        println("Your guesses were: ${game.guesses} ")

        println("Would you like to play again?")
        val response = readln()
        if (response.lowercase() == "no") {
            playAgain = false
        }
    } while (playAgain)
}


fun main() {
    runTests()
    playGame(readInt())
}

fun runTests() {
    runTests1()
    runTests2()
    runTests3()
    runTests4()
    runTests5()
    println("All tests pass.")
    testRepeatedWords()
}

/**
 * AI GENERATED TESTS, HUMAN PROVED
 */
fun runTests5() {
    val game = WordleGame("ABCDE")
    assertEquals(0, game.numGuesses)
    assertFalse(game.wordFound)
    assertEquals(".....", game.makeGuess("FGHIJ"))
    assertEquals("++*++", game.makeGuess("EDCBA"))
    assertEquals(".*+++", game.makeGuess("ZBDEC"))
    assertFalse(game.wordFound)
    assertEquals(3, game.numGuesses)
    assertEquals(".....", game.makeGuess("FGHIJ"))
    assertEquals(3, game.numGuesses)
    assertEquals("*****", game.makeGuess("ABCDE"))
    assertEquals(4, game.numGuesses)
    assertTrue(game.wordFound)
    assertEquals(
        mutableListOf("FGHIJ", "EDCBA", "ZBDEC", "ABCDE"),
        game.guesses,
    )

}

fun runTests1() {
    // Test for a 1-letter word
    val game = WordleGame("A")
    assertEquals(0, game.numGuesses) // Verify no guesses initially
    assertFalse(game.wordFound) // Verify the word has not been found
    assertEquals("*", game.makeGuess("A")) // Guess is correct
    assertTrue(game.wordFound) // Verify the word is now found
    assertEquals(1, game.numGuesses) // Verify number of guesses made
    assertEquals(mutableListOf("A"), game.guesses) // Verify the guesses list
}

fun runTests2() {
    // Test for a 2-letter word
    val game = WordleGame("AB")
    assertEquals(0, game.numGuesses) // Verify no guesses initially
    assertFalse(game.wordFound) // Verify the word has not been found
    assertEquals("..", game.makeGuess("CD")) // No letters match
    assertEquals("**", game.makeGuess("AB")) // Both letters match
    assertTrue(game.wordFound) // Verify the word is now found
    assertEquals(2, game.numGuesses) // Verify number of guesses made
    assertEquals(mutableListOf("CD", "AB"), game.guesses) // Verify the guesses list
}

fun runTests3() {
    // Test for a 3-letter word
    val game = WordleGame("CAT")
    assertEquals(0, game.numGuesses) // Verify no guesses initially
    assertFalse(game.wordFound) // Verify the word has not been found
    assertEquals("...", game.makeGuess("DOG")) // No letters match
    assertEquals("++*", game.makeGuess("ACT")) // One letter matches, 2 found
    assertEquals("***", game.makeGuess("CAT")) // All letters match
    assertTrue(game.wordFound) // Verify the word is now found
    assertEquals(3, game.numGuesses) // Verify number of guesses made
    assertEquals(mutableListOf("DOG", "ACT", "CAT"), game.guesses) // Verify the guesses list
}

fun runTests4() {
    // Test for a 4-letter word
    val game = WordleGame("WORD")
    assertEquals(0, game.numGuesses) // Verify no guesses initially
    assertFalse(game.wordFound) // Verify the word has not been found
    assertEquals("....", game.makeGuess("TEST")) // No letters match
    assertEquals("++++", game.makeGuess("DROW")) // No letters match, 5 found
    assertEquals("****", game.makeGuess("WORD")) // All letters match
    assertTrue(game.wordFound) // Verify the word is now found
    assertEquals(3, game.numGuesses) // Verify number of guesses made
    assertEquals(mutableListOf("TEST", "DROW", "WORD"), game.guesses) // Verify the guesses list
}

/**
 * tests to make sure that a repeated word is not counted and still returns the feedback
 */
fun testRepeatedWords() {
    val game = WordleGame("BRUNO")
    assertEquals("++*++", game.makeGuess("ONURB"))
    assertEquals(1, game.numGuesses)
    assertEquals("++*++", game.makeGuess("ONURB"))
    assertEquals(1, game.numGuesses)
    println("test repeated cases passed!")
}


