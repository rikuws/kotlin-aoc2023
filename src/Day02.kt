fun main() {
  fun splitGameStr(gameStr: String): Pair<Int, String> {
    val gameIdAndGame = gameStr.split(":")
    val gameId = gameIdAndGame.first()
    val game = gameIdAndGame.last()

    return Pair(gameId.split(" ").last().toInt(), game)
  }

  fun gamePartColours(gamePart: String): List<Pair<Int, String>> {
    val cubes = gamePart.split(",")
    return cubes.map {
      val numAndColour = it.trim().split(" ")
      Pair(numAndColour.first().toInt(), numAndColour.last())
    }
  }

  fun gameHasTooManyCubes(gameStr: String, maxBlue: Int, maxRed: Int, maxGreen: Int): Boolean {
    val gameParts = gameStr.split(";")
    val gamePartsWithColours = gameParts.map { gamePartColours(it) }
    return gamePartsWithColours
        .filter { gamePart ->
          val res =
              gamePart.filter { it.second == "blue" }.sumOf { it.first } <= maxBlue &&
                  gamePart.filter { it.second == "red" }.sumOf { it.first } <= maxRed &&
                  gamePart.filter { it.second == "green" }.sumOf { it.first } <= maxGreen
          res
        }
        .size != gamePartsWithColours.size
  }

  fun maxNeededCubes(gameStr: String): Triple<Int, Int, Int> {
    val gameParts = gameStr.split(";")
    val gamePartsWithColours = gameParts.map { gamePartColours(it) }
    val maxBlue =
        gamePartsWithColours.maxOf { gamePart ->
          gamePart.filter { it.second == "blue" }.sumOf { it.first }
        }
    val maxRed =
        gamePartsWithColours.maxOf { gamePart ->
          gamePart.filter { it.second == "red" }.sumOf { it.first }
        }
    val maxGreen =
        gamePartsWithColours.maxOf { gamePart ->
          gamePart.filter { it.second == "green" }.sumOf { it.first }
        }
    return Triple(maxBlue, maxRed, maxGreen)
  }

  fun part1(input: List<String>): Int {
    val games = input.map { splitGameStr(it) }
    return games.filter { !gameHasTooManyCubes(it.second, 14, 12, 13) }.sumOf { it.first }
  }

  fun part2(input: List<String>): Int {
    val games = input.map { splitGameStr(it) }

    return games.sumOf { game ->
      val maxCubes = maxNeededCubes(game.second)
      maxCubes.first * maxCubes.second * maxCubes.third
    }
  }

  val testInput = readInput("Day02_test")
  check(part2(testInput) == 2286)

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
