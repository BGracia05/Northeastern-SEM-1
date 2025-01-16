/**
 * A user interface to [WordleGame].
 */
interface UserInterface {
    /**
     * Reads a guess.
     */
    fun readGuess(): String

    /**
     * Displays the error [msg].
     */
    fun showError(msg: String)

    /**
     * Shows the [matchString] generated for [guess], in the
     * format returned by[WordleGame.receiveGuess].
     */
    fun showFeedback(guess: String, matchString: String)

    /**
     * Announces that the player has lost after being unable
     * to guess [secretWord].
     */
    fun showLoss(secretWord: String)

    /**
     * Announces that the player has won after [numGuesses].
     */
    fun showWin(numGuesses: Int)
}
