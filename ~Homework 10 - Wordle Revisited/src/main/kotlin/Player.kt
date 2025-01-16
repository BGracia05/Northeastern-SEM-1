abstract class Player(protected val ui: UserInterface) {
    /**
     * Prepares for a new game.
     */
    abstract fun startNewGame()

    /**
     * Generates a guess of the secret word.
     */
    abstract fun generateGuess(): String

    /**
     * Incorporates feedback in [matchString] for its previous [guess].
     */
    abstract fun incorporateFeedback(guess: String, matchString: String)
}

private const val HUMAN_ARG = "--human"
private const val AI_ARG = "--ai"
fun isHumanPlayer(args: Array<String>) =
    when (args.toList()) {
        listOf(HUMAN_ARG) -> true
        listOf(AI_ARG) -> false
        else -> throw IllegalArgumentException("You must provide program argument $HUMAN_ARG or $AI_ARG")
    }