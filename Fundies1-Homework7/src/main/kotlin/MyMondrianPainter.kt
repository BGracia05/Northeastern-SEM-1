import java.awt.Color

class MyMondrianPainter() : MondrianPainter() {
    override fun fillColor(): Color {
        return when ((0..5).random()) {
            0 -> Color.RED
            1 -> Color.BLUE
            2 -> Color.YELLOW
            3 -> Color.PINK
            4 -> Color.CYAN
            else -> Color.MAGENTA
        }
    }
}

/**
 * Creates a canvas and paints it in the style of Piet Mondrian.
 */
fun main() {
    require(REQUESTED_CANVAS_HEIGHT >= 4 * MIN_RECTANGLE_HEIGHT)
    require(REQUESTED_CANVAS_WIDTH >= 4 * MIN_RECTANGLE_WIDTH)
    MyMondrianPainter()
}
