import java.awt.Color
import kotlin.random.Random

/**
 * The desired canvas width, which must be at least 4 times [MIN_RECTANGLE_WIDTH].
 */
const val REQUESTED_CANVAS_WIDTH = 800

/**
 * The desired canvas height, which must be at least 4 times [MIN_RECTANGLE_HEIGHT].
 */
const val REQUESTED_CANVAS_HEIGHT = 400

/**
 * The minimum width of a colored rectangle.
 */
const val MIN_RECTANGLE_WIDTH = 80

/**
 * The minimum height of a colored rectangle.
 */
const val MIN_RECTANGLE_HEIGHT = 80

// seed for random number generator
const val SEED = 2500

open class MondrianPainter {
    // Use this property when generating random numbers.
    val random = Random(SEED)

    private val canvas =
        Canvas(
            "Mondrian Painter",
            this,
            REQUESTED_CANVAS_WIDTH,
            REQUESTED_CANVAS_HEIGHT,
        )

    /**
     * Performs a side-by-side split of the region specified by [x], [y],
     * [width], and [height], if it is wide enough to split, and calls
     * [doMondrian] on each of the two smaller regions. If the original
     * region is too narrow to split, no action is taken.
     *
     * @return true if the original region was wide enough to split, false
     * otherwise
     */
    protected open fun splitLeftRight(x: Int, y: Int, width: Int, height: Int): Boolean {
        if (width < MIN_RECTANGLE_WIDTH * 2) { // B.C
            return false
        }
        val xOffset = random.nextInt(MIN_RECTANGLE_WIDTH, width - MIN_RECTANGLE_WIDTH)
        doMondrian(x, y, xOffset, height) // left region
        doMondrian(x + xOffset, y, width - xOffset, height) // right region
        return true
    }

    /**
     * Performs an over-under split of the region specified by [x], [y],
     * [width], and [height], if it is tall enough to split, and calls
     * [doMondrian] on each of the two smaller regions. If the original
     * region is too short to split, no action is taken.
     *
     * @return true if the original region was tall enough to split, false
     * otherwise
     */
    protected open fun splitTopBottom(x: Int, y: Int, width: Int, height: Int): Boolean {
        if (height < MIN_RECTANGLE_HEIGHT * 2) { // B.C
            return false
        }
        val yOffset = random.nextInt(MIN_RECTANGLE_HEIGHT, height - MIN_RECTANGLE_HEIGHT)
        doMondrian(x, y, width, yOffset) // top region
        doMondrian(x, y + yOffset, width, height - yOffset) // bottom region
        return true
    }


    /**
     * Performs an horizontal and vertical split of the region specified
     * by [x], [y], [width], and [height], if it is both wide and tall enough
     * to split, and calls [doMondrian] on each of the four smaller regions.
     * If the original region is too small to split, no action is taken.
     *
     * @return true if the original region could be split, false otherwise
     */
    protected open fun split4Way(x: Int, y: Int, width: Int, height: Int): Boolean {
        if (height < MIN_RECTANGLE_HEIGHT * 2 || width < MIN_RECTANGLE_WIDTH * 2) { // B.C
            return false
        }
        val xOffset = random.nextInt(MIN_RECTANGLE_WIDTH, width - MIN_RECTANGLE_WIDTH)
        val yOffset = random.nextInt(MIN_RECTANGLE_HEIGHT, height - MIN_RECTANGLE_HEIGHT)

        doMondrian(x + xOffset, y, width - xOffset, yOffset) // top-right region
        doMondrian(x, y, xOffset, yOffset) // top-left region
        doMondrian(x, y + yOffset, xOffset, height - yOffset) // bottom-left region
        doMondrian(x + xOffset, y + yOffset, width - xOffset, height - yOffset) // bottom-right region
        return true
    }

    protected open fun fillColor(): Color {
        return if (random.nextBoolean()) Color.WHITE else when (random.nextInt(3)) {
            0 -> Color.RED
            1 -> Color.BLUE
            else -> Color.YELLOW
        }
    }

    /**
     * Divides the region with the given [x] and [y] coordinates and having
     * width [width] and height [height] into one or more colored rectangles,
     * in the style of Piet Mondrian.
     */
    fun doMondrian(x: Int, y: Int, width: Int, height: Int) {
        if (width > REQUESTED_CANVAS_WIDTH / 2 && height > REQUESTED_CANVAS_HEIGHT / 2) {
            split4Way(x, y, width, height)
            return
        }
        if (width > REQUESTED_CANVAS_WIDTH / 2 && height < REQUESTED_CANVAS_HEIGHT / 2) {
            splitLeftRight(x, y, width, height)
            return
        }

        if (height > REQUESTED_CANVAS_HEIGHT / 2 && width < REQUESTED_CANVAS_WIDTH / 2) {
            splitTopBottom(x, y, width, height)
            return
        }
        when (random.nextInt(3)) {
            0 -> if (width >= MIN_RECTANGLE_WIDTH * 2) {
                splitLeftRight(x, y, width, height)
                return
            }

            1 -> if (height >= MIN_RECTANGLE_HEIGHT * 2) {
                splitTopBottom(x, y, width, height)
                return
            }

            2 -> if (width >= MIN_RECTANGLE_WIDTH * 2 && height >= MIN_RECTANGLE_HEIGHT * 2) {
                split4Way(x, y, width, height)
                return
            }
        }
        // 5. If none of the above conditions code caused a split method to be
        //    called, fill the entire region with a single color. Half the time,
        //    the color should be white. The other half of the time, choose
        //    randomly among red, yellow, and blue. The outline color should
        //    always be black. You can modify the below sample code, which
        //    always draws a yellow rectangle with a black outline
        canvas.drawRectangle(
            x,
            y,
            width,
            height,
            fillColor = fillColor(),
            outlineColor = Color.BLACK,
        )
    }

    /**
     * Handles a click at the specified [x]-[y] coordinates.
     */
    fun handleClick(x: Int, y: Int) {
        recolorRectangle(x, y)
    }

    /**
     * Changes the fill color of the rectangle containing ([x], [y]).
     */
    fun recolorRectangle(x: Int, y: Int) {
        val initialColor = canvas.getColorAt(x, y)

        var leftBoundary = x
        var rightBoundary = x
        var topBoundary = y
        var bottomBoundary = y

        while (leftBoundary > 0 && canvas.getColorAt(leftBoundary - 1, y) == initialColor) {
            leftBoundary += -1
        }
        while (rightBoundary < canvas.width - 1 && canvas.getColorAt(rightBoundary + 1, y) == initialColor) {
            rightBoundary += 1
        }
        while (topBoundary > 0 && canvas.getColorAt(x, topBoundary - 1) == initialColor) {
            topBoundary += -1
        }
        while (bottomBoundary < canvas.height - 1 && canvas.getColorAt(x, bottomBoundary + 1) == initialColor) {
            bottomBoundary += 1
        }
        canvas.drawRectangle(
            leftBoundary,
            topBoundary,
            rightBoundary - leftBoundary + 1,
            bottomBoundary - topBoundary + 1,
            fillColor = Color.RED,
            outlineColor = Color.BLACK
        )
    }
}

/**
 * Creates a canvas and paints it in the style of Piet Mondrian.
 */
fun main() {
    require(REQUESTED_CANVAS_HEIGHT >= 4 * MIN_RECTANGLE_HEIGHT)
    require(REQUESTED_CANVAS_WIDTH >= 4 * MIN_RECTANGLE_WIDTH)
    MondrianPainter()
}
