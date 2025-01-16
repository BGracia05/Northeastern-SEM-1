import java.io.File

/**
 * Reads words from [filename], returning a list of the words.
 */
fun readWordsFromFile(filename: String): List<String> =
    File("data/dictionaries/$filename").readLines()
