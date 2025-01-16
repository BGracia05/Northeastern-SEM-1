class HumanPlayer(ui: UserInterface) : Player(ui) {
    var game: WordleGame? = null

    override fun startNewGame() {
        // no action required
    }

    override fun generateGuess(): String {
        return ui.readGuess()
    }

    override fun incorporateFeedback(guess: String, matchString: String) {
        // no action required
    }
}
