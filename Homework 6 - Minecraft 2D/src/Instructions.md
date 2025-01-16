## Instructions

### Preparation

Review your lecture notes, and read the tips at the bottom of this page. They
can save you hours of frustration.

Follow the instructions in the Canvas videos to build and view the KDoc and
to build the program. Run the program and press some keys.

### Sound Effects

Currently, there are no sound effects. For this section, you will have
mobs make sounds if a random value is the range 0-1 is less than a
constant value.

1. The constant `NOISINESS` is defined in [Mob.kt](Mob.kt). It is the per-tick
   probability that a mob will make a sound. For example, a value of `0.1`
   means there is a 10% chance of making a noise on any given tick. Follow
   the instruction in the first comment in `Mob.tick()` to enable sounds
   effects.
2. Test the game and make sure the sound is played. Once you get it working,
   feel free to adjust the constant up or down.

### Movement

In this section, you will add movement for living entities. The player's
movement will be controlled by direction keys (either the arrows or
[WASD](https://en.wikipedia.org/wiki/Arrow_keys#WASD_keys). For now,
mobs will move randomly into unoccupied positions.

1. Implement `LivingEntity.moveRight()`.
2. Add code to `Player.tick()` to call `moveRight()` if the last
   key pressed was `arrow-right` or `s`. Test it. The player should now move
   right when you press the right arrow or `s` key. See what happens if you try
   going off the edge.
3. Repeat the previous steps for moving left, up, and down, and verify that
   the player can move in all directions. I recommend creating a private helper
   function `move(deltaX: Int, deltaY: Int)` that each of the 4 methods call
   to avoid duplicating code. Test moving in all directions and make sure 
   you can't move into the square with the zombie.
4. Implement `LivingEntity.moveRandomly()`, and add a call from `Mob.tick()`.
   `moveRandomly()` should call with equal probability one of the four
   methods you created, moving only into unoccupied spaces. If the position
   it first chooses is occupied, it should move into a different neighboring
   position, unless all those are occupied too. The zombie should now
   move randomly around the screen (but not on top of the player).

### Chicken

In this section, you will add a second mob, a chicken.

1. Add a `Chicken` by seeing how [Zombie](Zombie.kt) was added. Specifically,
   you will need to create a new `Chicken` class that extends `Mob`. Chicken
   image and sound files have been provided. Chickens must be passive, but you
   can choose their other stats.
2. Test the game.

### Zombie attack

To prepare for battle, aggressive mobs will move toward the player and
attack when they get within range.

1. Add `Game.calculateDistance()` to calculate the Euclidean distance
   between two entities. The result should be equal to the square root of
   the sum of the squares of the difference between the x-coordinates and
   the y-coordinates. For example, if two entities were at (1, 5) and
   (3, 8), the distance would be the square root of (3-1)^2 + (8-5)^2.
2. Implement the method `LivingEntity.moveTowards()`, which attempts to move the
   living entity closer to the mob specified as its argument.
3. Complete the implementation of `Mob.tick()` as described in the
   comments.
4. Test the game. The zombie should now move toward the player and
   attack within range.

### Battle
In this section, the player will gain the ability to fight back.

1. Add one or more appropriately-named methods to [Player](Player.kt) for the
   player to attack all hostile mobs within fighting distance (i.e., whose
   Euclidean distance is less than or equal to `FIGHTING_RANGE`). If you
   make the methods `private`, you don't need to write KDoc.
2. Modify [Player.tick()](Player.kt) so, when the slash key (`/`) is pressed,
   your new method is called.
3. If you haven't yet, make sure your code handles a key press of "space",
   which should do nothing.
4. Test your code.
5. When you get it working, clean up your code. If you had used `if-else`,
   switch to `when`.

### Addition

Make one or two additions, being careful that they do not interfere with
grading the above items. Here are a few possibilities, any of which would get
full credit:
* Add a "hyperspace" key that teleports the player, causing instant death if
  they collide with another entity.
* Have chickens randomly lay eggs (a new `LivingEntity` that you would create).
  Eggs would randomly hatch into chickens once in a while. An egg image has been
  provided.
* Add a new mob.
We look forward to seeing other ideas you come up with.

## Tips

The #1 tip is to ask for help on Piazza or in office hours if anything is
unclear.

### Building

* Build the KDoc with `Gradle > Tasks > documentation > dokkaHtml`, as
  demonstrated in the Canvas video. Tip: You can copy it to a directory
  outside the project so it won't get deleted when you clean the project.
* Run the application with `Gradle > Tasks > application > run`. For subsequent
  runs, you should be able to click the green play button (triangle) on the 
  top of IntelliJ, towards the right.
* If you get a strange compiler error, such as `Changes are not tracked,
  unable determine incremental changes.`, or strange run time errors, or if
  your changes don't seem to be reflected when you run the app, try each
  of the following, as shown in a [video](https://northeastern.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=458c3135-d54d-4ff8-b5f0-b203014d61f2)
  (and document your issues in `Summary.md`):
    * `Gradle > Tasks > build > clean`
    * `File > Invalidate Caches...Invalidate and Restart`
  There is a video demonstrating how to do this.
* Ignore warnings about deprecated Gradle features as long as your project runs.

### Implementation and Debugging
* Always read provided KDoc carefully when implementing a method.
* If you prefer, you can modify [Player](Player.kt) to be Alex rather than
  Steve.
* Text output
   * If the game grid uses up too much of your screen for you to see the
     messages in the text area, you can:
       - Change the constant `NUM_ROWS` in [Minecraft2D.kt](Minecraft2D.kt).
       - View the text in IntelliJ's `Run` pane.
   * You may find it useful to call `clearText()` from `Game.tick()`.
   * You can print debugging messages by calling `Game.addText()`.
* If you don't know the name of a key, press it in the game and see what message
  is printed.
* To access properties or methods in `Game`, use the class name, since it is a
  singleton (the only instance of its class). For example, `Game.player` gets
  the player.
* If you make use of the `Random` library, make sure 'kotlin.random.Random' is
  imported, not `java.util.Random`.
* Make sure the player doesn't attack itself!
* The [Minecraft Wiki](https://minecraft.fandom.com/wiki/Minecraft_Wiki) is
  a useful resource.
