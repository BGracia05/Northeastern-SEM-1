package wordle

import assertEquals
import assertThrows
import kotlin.IllegalArgumentException

class WordleGame(
    val secretWord: String,
) {
    /**
     * Returns a string of the same length as [guess] and [secretWord]
     * indicating how closely they match. In positions where the characters are
     * the same, the string will hold '*'. In positions where the character in
     * the guess appears elsewhere in the secret word, the string will hold '+'.
     * In positions where the character in the guess is not in the secret word,
     * the string will hold '.'
     *
     * @throws IllegalArgumentException if the lengths of [guess] and
     * [secretWord] differ or if either contains repeated characters
     */
    fun makeMatchString(guess: String): String {
        if (guess.length != secretWord.length) {
            throw IllegalArgumentException("Lengths do not match up!")
        }
        if (guess.toList().distinct().size != guess.length || secretWord.toList()
                .distinct().size != secretWord.length
        ) {
            throw IllegalArgumentException("Repetition in chars")
        }
        val result = CharArray(guess.length)
        val secretCharCount = mutableMapOf<Char, Int>()
        
        for (char in secretWord) {
            secretCharCount[char] = secretCharCount.getOrDefault(char, 0) + 1
        }

        for (i in guess.indices) {
            if (guess[i] == secretWord[i]) {
                result[i] = '*'
                secretCharCount[guess[i]] = secretCharCount.getValue(guess[i]) - 1
            } else {
                result[i] = '.'
            }
        }

        for (i in guess.indices) {
            if (result[i] == '.') {
                if (secretCharCount.getOrDefault(guess[i], 0) > 0) {
                    result[i] = '+'
                    secretCharCount[guess[i]] = secretCharCount.getValue(guess[i]) - 1
                }
            }
        }

        return String(result)
    }
}

fun main() {
    testMakeMatchString2()
    testMakeMatchString5()
    testMakeMatchStringExceptions()
    println("All tests passed")
}

fun testMakeMatchString2() {
    val game = WordleGame("AB")
    assertEquals("..", game.makeMatchString("CD"))
    assertEquals("**", game.makeMatchString("AB"))
    assertEquals("++", game.makeMatchString("BA"))
    assertEquals("+.", game.makeMatchString("BC"))
}

fun testMakeMatchString5() {
    val game = WordleGame("ABCDE")
    assertEquals(".....", game.makeMatchString("FGHIJ"))
    assertEquals("++*++", game.makeMatchString("EDCBA"))
    assertEquals(".*+++", game.makeMatchString("ZBDEC"))
    assertEquals(".....", game.makeMatchString("FGHIJ"))
    assertEquals("*****", game.makeMatchString("ABCDE"))
}

fun testMakeMatchStringExceptions() {
    val game1 = WordleGame("AABCD")
    assertThrows<IllegalArgumentException> { game1.makeMatchString("ABCDE") }
    val game2 = WordleGame("ABC")
    assertThrows<IllegalArgumentException> { game2.makeMatchString("AXX") }
}
