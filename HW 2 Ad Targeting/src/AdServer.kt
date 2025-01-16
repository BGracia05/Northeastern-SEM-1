// Congratulations! You've been invited to do the following
// online assessment for a co-op with tech giant Oodle.
//
// 1. Add the 3+ constants from the pre-exercise to the Gender enum.
// 2. Define at least 2 age-based constants where indicated below to
//    make your later ad-serving code more readable and maintainable.
//    Choose general names, such as MIN_ADULT_AGE rather than ones that
//    refer to ads, such as MIN_AGE_FOR_DATING_AD. You can also use
//    numeric literals. (Look up terms if you don't understand them.
//    They may appear on tests!)
// 3. Add properties minAge and maxAge to Ad, and set values for each
//    ad. For example, minAge for the dating ad might be MIN_ADULT_AGE.
// 4. Implement the provided fetchAd() function. You must use both "if"
//    and "when". Paste in the tests from the pre-exercise. Uncomment
//    the call to runTests() in main(). Run the tests, and see if they
//    all pass. In your write-up, you will need to describe the testing
//    and debugging process, so take notes.
// 5. Create a new data class named "Person". A person should have an
//    age (Int), gender (Gender), and income (Int). Use your judgment
//    as to which properties should be changeable.
// 6. Write a new fetchAd() method (without removing the original one)
//    that takes a single parameter of type Person and returns an Ad.
//    Instead of duplicating the code in your original fetchAd() method,
//    have your new method call your old method, passing the appropriate
//    properties as arguments.
// 7. Create a new function named "testFetchAdPerson" that tests your
//    new fetchAd() method. Modify runTests() to call this new function.
enum class Gender {
    Female, Male, Nonbinary
    // 1. Add the 3+ constants from the pre-exercise to the Gender enum.
}

// Age-based constants
const val MIN_AGE_FOR_PERSONALIZATION = 13
const val LEGAL_WORKING_AGE = 16
const val MIN_ADULT_AGE = 18
const val MAX_ADULT_AGE = 60
const val MAX_SENIOR_AGE = 99
const val AVG_AGE_FOR_PARTNER = 28
// 2. Define at least 2 age-based constants where indicated below to
//    make your later ad-serving code more readable and maintainable.
//    Choose general names, such as MIN_ADULT_AGE rather than ones that
//    refer to ads, such as MIN_AGE_FOR_DATING_AD.

// 3. Add properties minAge and maxAge to Ad, and set values for each
//    ad. For example, minAge for the dating ad might be MIN_ADULT_AGE.
//    You can also use numeric literals. (Look up terms if you don't understand
//    them. They may appear on tests!) Be sure to update the KDoc.

/**
 * Ads that may be shown to users.
 *
 * @property text the text of the ad
 * @property revenue the number of cents earned per click
 */
// Ad.Diet was used to have a redirection for else statements that are required in when statements
enum class Ad(val text: String, val revenue: Int, val minAge: Int, val maxAge: Int) {
    Diet("Lose weight now!", 5, MIN_AGE_FOR_PERSONALIZATION, MAX_SENIOR_AGE),
    Dating("Meet other singles!", 4, MIN_ADULT_AGE, AVG_AGE_FOR_PARTNER),
    Lego("Fun to step on!", 1, MIN_AGE_FOR_PERSONALIZATION, MAX_SENIOR_AGE),
    Pet("You need a friend!", 1, MIN_ADULT_AGE, MAX_ADULT_AGE),
    PetToy("Amuse your pet!", 2, MIN_AGE_FOR_PERSONALIZATION, MAX_ADULT_AGE),
    Pokemon("Gotta catch 'em all!", 2, MIN_AGE_FOR_PERSONALIZATION, MAX_ADULT_AGE),
    Retirement("Join AARP", 2, MAX_ADULT_AGE, MAX_SENIOR_AGE),
    Work("Apply for a job at Oodle!", 2, LEGAL_WORKING_AGE, MAX_ADULT_AGE),
}

/**
 * Fetches an ad based on the user's [gender], [age], and
 * [income], unless the age is under [MIN_AGE_FOR_PERSONALIZATION],
 * in which case no personalization is permitted.
 */

// since income automatically limits the possibility to 3 variables (all 3 genders), I started off with it followed by gender (if it was needed) because it is limited to 5 variables (5 ads)
// and finally I closed it off with age as it has the widest range of variables. I did this to avoid overlapping.
fun fetchAd(gender: Gender, age: Int, income: Int): Ad {
    return when {
// Gender was not required because all genders pertain to the work ad
        income in 0..19999 -> {
            if (age in Ad.Work.minAge..Ad.Work.maxAge) {
                Ad.Work
            } else {
                Ad.Diet
            }
        }
// Gender was not required because all genders pertain to the pet ad
        income in 20000..39999 -> {
            if (age in Ad.Pet.minAge..Ad.Pet.maxAge) {
                Ad.Pet
            } else {
                Ad.Diet
            }
        }
// as stated before, these when statements are carefully thought out to use income first, gender, and then age in order to avoid overlapping
        income in 40000..59999 -> {
            when (gender) {
                Gender.Male -> {
                    if (age in Ad.Pokemon.minAge..Ad.Pokemon.maxAge) Ad.Pokemon else Ad.Diet
                }

                Gender.Nonbinary -> {
                    if (age in Ad.PetToy.minAge..Ad.PetToy.maxAge) Ad.PetToy else Ad.Diet
                }

                Gender.Female -> {
                    if (age in Ad.Dating.minAge..Ad.Dating.maxAge) Ad.Dating else Ad.Diet
                }
            }
        }

        income in 60000..99999 -> {
            when (gender) {
                Gender.Male -> {
                    if (age in Ad.Dating.minAge..Ad.Dating.maxAge) Ad.Dating else Ad.Diet
                }

                Gender.Female, Gender.Nonbinary -> {
                    if (age in Ad.Lego.minAge..Ad.Lego.maxAge) Ad.Lego else Ad.Diet
                }
            }
        }

        income in 100000..999999 -> {
            if (age in Ad.Retirement.minAge..Ad.Retirement.maxAge) {
                Ad.Retirement
            } else {
                Ad.Diet
            }
        }

        else -> { // if all else fails
            Ad.Diet
        }
    }
    // 4a. Implement the provided fetchAd() function. You must use
    //     both "if" and "when".

}

fun testDating1() {
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 18, 40000))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 18, 59999))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 18, 49999))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 28, 40000))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 28, 59999))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 28, 49999))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 23, 40000))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 23, 59999))
    assertEquals(Ad.Dating, fetchAd(Gender.Female, 23, 49999))
}


fun testDating2() {
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 18, 60000))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 18, 99999))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 18, 79999))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 28, 60000))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 28, 99999))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 28, 79999))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 23, 60000))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 23, 99999))
    assertEquals(Ad.Dating, fetchAd(Gender.Male, 23, 79999))
}


fun testLego3() {
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 13, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 13, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 13, 79999))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 99, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 99, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 99, 79999))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 56, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 56, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Nonbinary, 56, 79999))
}


fun testLego4() {
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 13, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 13, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 13, 79999))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 99, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 99, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 99, 79999))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 56, 60000))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 56, 99999))
    assertEquals(Ad.Lego, fetchAd(Gender.Female, 56, 79999))
}


fun testPet5() {
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 18, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 18, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 18, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 60, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 60, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 60, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 39, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 39, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Female, 39, 29999))
}


fun testPet6() {
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 18, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 18, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 18, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 60, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 60, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 60, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 39, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 39, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Male, 39, 29999))
}


fun testPet7() {
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 18, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 18, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 18, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 60, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 60, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 60, 29999))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 39, 20000))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 39, 39999))
    assertEquals(Ad.Pet, fetchAd(Gender.Nonbinary, 39, 29999))
}


fun testPetToy8() {
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 13, 40000))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 13, 59999))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 13, 49999))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 60, 40000))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 60, 59999))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 60, 49999))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 36, 40000))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 36, 59999))
    assertEquals(Ad.PetToy, fetchAd(Gender.Nonbinary, 36, 49999))
}


fun testPokemon9() {
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 13, 40000))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 13, 59999))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 13, 49999))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 60, 40000))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 60, 59999))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 60, 49999))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 36, 40000))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 36, 59999))
    assertEquals(Ad.Pokemon, fetchAd(Gender.Male, 36, 49999))
}


fun testRetirement10() {
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 60, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 60, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 60, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 99, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 99, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 99, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 79, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 79, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Female, 79, 549999))
}


fun testRetirement11() {
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 60, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 60, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 60, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 99, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 99, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 99, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 79, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 79, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Male, 79, 549999))
}


fun testRetirement12() {
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 60, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 60, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 60, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 99, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 99, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 99, 549999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 79, 100000))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 79, 999999))
    assertEquals(Ad.Retirement, fetchAd(Gender.Nonbinary, 79, 549999))
}


fun testWork13() {
    assertEquals(Ad.Work, fetchAd(Gender.Female, 16, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 16, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 16, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 60, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 60, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 60, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 38, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 38, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Female, 38, 9999))
}


fun testWork14() {
    assertEquals(Ad.Work, fetchAd(Gender.Male, 16, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 16, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 16, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 60, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 60, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 60, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 38, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 38, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Male, 38, 9999))
}


fun testWork15() {
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 16, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 16, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 16, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 60, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 60, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 60, 9999))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 38, 0))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 38, 19999))
    assertEquals(Ad.Work, fetchAd(Gender.Nonbinary, 38, 9999))
}

// 4b. Paste in the tests from the pre-exercise.

// 4c. Uncomment the call to runTests() in main(). Run the tests, and see
//     if they all pass. In your write-up, you will need to describe the
//     testing and debugging process, so take notes.
fun main() {
    testAll()
}

fun testAll() {
    testFetchAdPerson()
    testDating1()
    testDating2()
    testLego3()
    testLego4()
    testPet5()
    testPet6()
    testPet7()
    testPetToy8()
    testPokemon9()
    testRetirement10()
    testRetirement11()
    testRetirement12()
    testWork13()
    testWork14()
    testWork15()
    print("All tests pass.")
}

// I used a val, assuming that a person will gain a new profile every birthday, gender change, and income change
// if this was a tracking profile of "person" I would've used var because all variables are subject to change in the course of the person's life.
data class Person(val age: Int, val gender: Gender, val income: Int)
// 5. Create a new data class named "Person". A person should have an
//    age (Int), gender (Gender), and income (Int). Use your judgment
//    as to which properties should be changeable.

// 6. Write a new fetchAd() method (without removing the original one)
//    that takes a single parameter of type Person and returns an Ad.
//    Instead of duplicating the code in your original fetchAd() method,
//    have your new method call your old method, passing the appropriate
//    properties as arguments.
fun fetchAd(person: Person): Ad {
    return fetchAd(person.gender, person.age, person.income)
}

// 7. Create a new function named "testFetchAdPerson" that tests your
//    new fetchAd() method. Modify runTests() to call this new function.

val person1 = Person(27, Gender.Male, 75000)
val person2 = Person(30, Gender.Female, 7583)
val person3 = Person(87, Gender.Nonbinary, 872034)
val person4 = Person(16, Gender.Female, 65000)
fun testFetchAdPerson() {
    assertEquals(Ad.Dating, fetchAd(person1))
    assertEquals(Ad.Work, fetchAd(person2))
    assertEquals(Ad.Retirement, fetchAd(person3))
    assertEquals(Ad.Lego, fetchAd(person4))
    println("All Persons' Test Passed")
}
// Do not modify the following code.
/**
 * Verifies that [actual] is equal to [expected].
 *
 * @throws AssertionError if they are not equal
 */
fun assertEquals(expected: Any, actual: Any) {
    if (expected != actual) {
        throw AssertionError("Expected $expected, got $actual")
    }
}
