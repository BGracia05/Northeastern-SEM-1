/**
 * Prompts a user to enter their name.
 *
 * @return the text entered by the user
 */
fun getUserName(): String {
    println("What is your name?")
    val name = readln()
    return name
}

// Prompts a user to enter their date of birth
// @return stores the year input
fun getBirthYear(): Int {
    println("What year were you born?")
    val year = Integer.valueOf(readln())
    return year
}

// prompts a user to enter their favorite music artist
// @return stores artist name input
fun favArtist(): String {
    println("Who is your favorite music artist?")
    val artist = readln()
    return artist
}

/**
 * Greets the user by [name].
 */
fun greetUser(name: String) {
    println("Hello, $name!")
}

/**
 * Carries on a brief conversation with a user.
 */
fun converse() {
    val name = getUserName()
    greetUser(name)
    val year = getBirthYear()
    informUserOfGeneration(year)
    val artist = favArtist()
    musicTasteCompliment(artist)
}

// compliments the user's favorite artist, unless its Taylor Swift
fun musicTasteCompliment(artist: String) {
    if (artist == "Taylor Swift" || artist == "taylor swift")
        println("ew that's disgusting")
    else {
        println("Awesome! I also like listening to $artist!")
    }
}

// gives information of what generation the user pertains to. If they say a year that is past 2025 or before 1946, they are called a liar and shamed by the function
fun informUserOfGeneration(year: Int) {
    if (year in 2013..2025)
        println("You were born in the year $year, which makes you part of Generation Alpha!")
    if (year in 1997..2012)
        println("You were born in the year $year, which makes you part of Generation Z!")
    if (year in 1981..1996)
        println("You were born in the year $year, which makes you part of the Millenials!")
    if (year in 1965..1980)
        println("You were born in the year $year, which makes you part of Generation X!")
    if (year in 1946..1964)
        println("You were born in the year $year, which makes you a part of The Baby Boomer Generation!")
    if (year > 2025 || year < 1946)
        println("Liar! shame on you.")
}
