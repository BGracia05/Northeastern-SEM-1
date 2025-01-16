# Instructions

You must use functional programming style in this assignment; that is, you must
not use `var` or mutable objects. You may use `val`. As always, you may not use
`!!`.

Every function you implement must use one or more higher-order functions,
such as `filter()`, `filterIsInstance()`, `filterNot()`, `map()`,
`mapNotNull()`, `sortedBy()`, `sortedByDescending()`, `any()`, `all()`, and
`count()`. Tests are provided.

There are two code files you need to modify:

* `MinecraftExercises.kt`
* `Wordle.kt`

Do not modify any other files.

## Minecraft Exercises

### Existing Functions

You will need to implement each of these functions in
[minecraft/MinecraftExercises.kt](minecraft/MinecraftExercises.kt):

* `countNeutralMobs()`
* `getNumEntitiesPrintedAs()`
* `isBehaviorPresent()`
* `getLivingEntityTypesSortedByX()`
* `isPlayerStrongest()`
* `getStrengthsOfNearbyThreats()`
* `getLocationList()`
* `getBlockNamesBeneathPlacedEntities()`
* `getDescriptionsOfEntitiesAndBlocks()`

The code you write should work even if new entity or block types are added
or their placement changes. For example, your code should not rely on spiders
being the only type of neutral mob, even though that is currently the case.

You should not modify `ClassHierarchy`, `Game`, or `Position`. There are some
minor changes from Homework 6, such as:

* an additional subclass of `Block`: `Grass`.
* additional subclasses of `Mob`.
* `Game.getPosition()` now throws an exception if its argument is not on the
  board, so you no longer have to check for a `null` return value.

Whenever you are asked to do something for all entities, use the property
[Game.placedEntities](minecraft/Game.kt).

### New Function

Create a new function that you think could be useful to a Minecraft player.
Implement it, and create KDoc and tests comparable to those for the existing
functions.

## Wordle

You will need to implement a single function in [WordleGame](wordle/Wordle.kt):
`makeMatchString()`. It takes a guess and returns the match string (such as
`"**.+."`).

You may find these useful:

* [String.indices](https://kotlinandroid.org/kotlin/string/kotlin-string-indices/)
* [String.toList()](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-list.html)
* [String.toSet()](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-set.html)
* [List<Char>.joinToString()](https://www.baeldung.com/kotlin/convert-list-to-string#converting-list-to-a-string-using-jointostring)
