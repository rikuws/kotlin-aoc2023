fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf { line ->
      val digits = line.filter { it.isDigit() }
      "${digits.first()}${digits.last()}".toInt()
    }
  }

  fun part2(input: List<String>): Int {
    val numsList =
        listOf(
            Pair("one", "1"),
            Pair("two", "2"),
            Pair("three", "3"),
            Pair("four", "4"),
            Pair("five", "5"),
            Pair("six", "6"),
            Pair("seven", "7"),
            Pair("eight", "8"),
            Pair("nine", "9"))

    return input.sumOf { line ->
      val digitWords =
          numsList
              .map { num ->
                listOf(
                    Pair(line.indexOf(num.first), num.second),
                    Pair(line.lastIndexOf(num.first), num.second))
              }
              .flatten()
              .filter { it.first != -1 }

      val digits =
          numsList
              .map { num ->
                listOf(
                    Pair(line.indexOf(num.second), num.second),
                    Pair(line.lastIndexOf(num.second), num.second))
              }
              .flatten()
              .filter { it.first != -1 }

      val lineNumbers = (digitWords + digits).sortedBy { it.first }
      "${lineNumbers.first().second}${lineNumbers.last().second}".toInt()
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  check(part2(testInput) == 281)

  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
