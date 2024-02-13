package tictactoe

fun main() {
    val grid = MutableList(9) { ' ' }
    var currentPlayer = 'X'
    var gameEnded = false
    var movesCount = 0

    printGrid(grid)

    while (!gameEnded) {
        val (row, col, validInput) = readCoordinates()

        if (!validInput) {
            println("You should enter numbers!")
            continue
        }

        if (row !in 1..3 || col !in 1..3) {
            println("Coordinates should be from 1 to 3!")
            continue
        }

        val index = (row - 1) * 3 + (col - 1)
        if (grid[index] != ' ') {
            println("This cell is occupied! Choose another one!")
        } else {
            grid[index] = currentPlayer
            printGrid(grid)
            movesCount++

            when {
                checkWin(grid, currentPlayer) -> {
                    println("$currentPlayer wins")
                    gameEnded = true
                }
                movesCount == 9 -> {
                    println("Draw")
                    gameEnded = true
                }
                else -> currentPlayer = if (currentPlayer == 'X') 'O' else 'X'
            }
        }
    }
}

fun readCoordinates(): Triple<Int, Int, Boolean> {
    val input = readLine()
    val coordinates = input?.split(" ")
    return if (coordinates != null && coordinates.size == 2) {
        try {
            Triple(coordinates[0].toInt(), coordinates[1].toInt(), true)
        } catch (e: NumberFormatException) {
            Triple(0, 0, false)
        }
    } else {
        Triple(0, 0, false)
    }
}

fun printGrid(grid: List<Char>) {
    println("---------")
    for (i in 0..2) {
        println("| ${grid[i * 3]} ${grid[i * 3 + 1]} ${grid[i * 3 + 2]} |")
    }
    println("---------")
}

fun checkWin(grid: List<Char>, player: Char): Boolean {
    val lines = listOf(
        0..2, 3..5, 6..8, // rows
        0..6 step 3, 1..7 step 3, 2..8 step 3, // columns
        0..8 step 4, 2..6 step 2 // diagonals
    )

    return lines.any { range ->
        range.map { grid[it] }.all { it == player }
    }
}
