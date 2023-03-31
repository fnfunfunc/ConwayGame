import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

private const val CELL_SIZE = 10
private const val GRID_COLOR = "#CCCCCC"
private const val DEAD_COLOR = "#FFFFFF"
private const val ALIVE_COLOR = "#000000"

fun main() {
    val canvas = document.getElementById("game-of-life-canvas") as? HTMLCanvasElement ?: return
    val universe = Universe()
    val width = universe.width
    val height = universe.height
    canvas.width = (CELL_SIZE + 1) * width + 1
    canvas.height = (CELL_SIZE + 1) * height + 1
    val canvasContext = canvas.getContext("2d") as? CanvasRenderingContext2D ?: return

    fun renderLoop() {
        universe.tick()
        drawGrid(canvasContext, width, height)
        drawCells(canvasContext, width, height, universe.cells)
        window.requestAnimationFrame { renderLoop() }
    }
    drawGrid(canvasContext, width, height)
    drawCells(canvasContext, width, height, universe.cells)
    window.requestAnimationFrame { renderLoop() }
}


@OptIn(ExperimentalStdlibApi::class)
fun drawGrid(canvasContext: CanvasRenderingContext2D, width: Int, height: Int) {
    canvasContext.beginPath()
    canvasContext.strokeStyle = GRID_COLOR.asDynamic()
    for (i in 0..<width) {
        canvasContext.moveTo((i * (CELL_SIZE + 1) + 1).toDouble(), 0.0)
        canvasContext.lineTo((i * (CELL_SIZE + 1) + 1).toDouble(), ((CELL_SIZE + 1) * height + 1).toDouble())
    }

    for (j in 0..<height) {
        canvasContext.moveTo(0.0, (j * (CELL_SIZE + 1) + 1).toDouble())
        canvasContext.lineTo(((CELL_SIZE + 1) * width + 1).toDouble(), (j * (CELL_SIZE + 1) + 1).toDouble())
    }

    canvasContext.stroke()
}

@OptIn(ExperimentalStdlibApi::class)
fun drawCells(canvasContext: CanvasRenderingContext2D, width: Int, height: Int, cells: Array<Cell>) {
    fun getIndex(row: Int, column: Int) = row * width + column

    canvasContext.beginPath()
    for (row in 0..<height) {
        for (column in 0..<width) {
            val index = getIndex(row, column)

            canvasContext.fillStyle = (if (cells[index] == Cell.Dead) DEAD_COLOR else ALIVE_COLOR).asDynamic()
            canvasContext.fillRect(
                (column * (CELL_SIZE + 1) + 1).toDouble(),
                (row * (CELL_SIZE + 1) + 1).toDouble(),
                CELL_SIZE.toDouble(),
                CELL_SIZE.toDouble()
            )
        }
    }
    canvasContext.stroke()
}

