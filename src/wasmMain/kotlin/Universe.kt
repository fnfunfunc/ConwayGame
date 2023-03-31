@OptIn(ExperimentalStdlibApi::class)
class Universe(val width: Int = 64, val height: Int = 64) {

    var cells: Array<Cell>

    init {
        cells = (0..< width * height).map { i ->
            if (i.mod(2) == 0 || i.mod(7) == 0) {
                Cell.Alive
            } else {
                Cell.Dead
            }
        }.toTypedArray()
    }

    private fun getIndex(row: Int, column: Int) = row * width + column

    private fun liveNeighborCount(row: Int, column: Int): Int {
        var count = 0
        for (deltaRow in arrayOf(height - 1, 0, 1)) {
            for (deltaColumn in arrayOf(width - 1, 0, 1)) {
                if (deltaRow == 0 && deltaColumn == 0) {
                    continue
                }

                val neighborRow = (row + deltaRow).mod(height)
                val neighborColumn = (column + deltaColumn).mod(width)

                val index = getIndex(neighborRow, neighborColumn)
                count += cells[index].ordinal
            }
        }
        return count
    }

    fun tick() {
        val next = cells.copyOf()
        for (row in 0..<height) {
            for (column in 0..<width) {
                val index = getIndex(row, column)
                val cell = cells[index]
                val liveNeighbor = liveNeighborCount(row, column)
                next[index] = when (cell) {
                    Cell.Alive -> {
                        if (liveNeighbor == 2 || liveNeighbor == 3) {
                            Cell.Alive
                        } else {
                            Cell.Dead
                        }
                    }
                    Cell.Dead -> {
                        if (liveNeighbor == 3) {
                            Cell.Alive
                        } else {
                            Cell.Dead
                        }
                    }
                }
            }
        }
        cells = next
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        cells.toList().chunked(width).forEach { line ->
            line.forEach { cell ->
                stringBuilder.append(if (cell == Cell.Dead) '◻' else '◼')
            }
            stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }
}