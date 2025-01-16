import java.lang.Thread.sleep

class ArtificialPlayer(ui: UserInterface) : Player(ui) {
    // Source of real Wordle word list: https://github.com/tabatkins/wordle-list
    private val wordList = readWordsFromFile("input-words.txt")
    private val constraints: MutableList<(String) -> Boolean> = mutableListOf()

    override fun startNewGame() {
        constraints.clear()
    }

    override fun generateGuess(): String {
        // Pause for one second so the play doesn't move too fast.
        sleep(1000L)

        // Return the first word that satisfies all the constraints.
        // You may want to write a helper method.
        return wordList.first { word -> checkIfSatisfied(word) } // replace this
    }

    override fun incorporateFeedback(guess: String, matchString: String) {
        // Add new constraints based on our last guess and the resulting
        // matchString, as described in Instructions.md.
        for (i in guess.indices) {
            val pointedChar = guess[i]
            val feedback = matchString[i]

            when (feedback) {
                '*' -> {
                    constraints.add { word -> word[i] == pointedChar }
                }

                '+' -> {
                    constraints.add { word -> pointedChar in word && word[i] != pointedChar }
                }

                '.' -> {
                    constraints.add { word -> !word.contains(pointedChar) }
                }
            }
        }
    }

    /**
     * Helper function that checks to see if a word satisfies the given contstraints
     * @returns a list that makes sure all of the words inside are within the constraint criteria
     */
    private fun checkIfSatisfied(word: String): Boolean {
        return constraints.all { constraint -> constraint(word) }
    }

}
