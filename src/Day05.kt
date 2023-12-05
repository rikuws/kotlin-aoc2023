fun parseSeeds(input: List<String>): Sequence<Long> {
  return input.first().split(":").last().trim().split(" ").asSequence().map { it.toLong() }
}

fun parseSeedsPart2(input: List<String>): Sequence<LongRange> {
  return input.first().split(":").last().trim().split(" ").asSequence().chunked(2).map {
    (it[0].toLong() ..< it[0].toLong() + it[1].toLong())
  }
}

fun goThroughSeedDestinations(ranges: List<List<SourceDestinationRange>>, seed: Long): Long {
  val soil = findDestination(ranges[0], seed)
  val fertilizer = findDestination(ranges[1], soil)
  val water = findDestination(ranges[2], fertilizer)
  val light = findDestination(ranges[3], water)
  val temperature = findDestination(ranges[4], light)
  val humidity = findDestination(ranges[5], temperature)
  return findDestination(ranges[6], humidity)
}

data class SourceDestinationRange(
    val sourceStart: Long,
    val destinationStart: Long,
    val rangeLength: Long
)

fun buildSourceToDestinationRanges(
    input: List<String>,
    start: String,
    end: String
): List<SourceDestinationRange> {
  val startIndex = input.indexOfFirst { it.startsWith(start) }
  val endIndex =
      if (end == "") {
        input.size
      } else {
        input.indexOfFirst { it.startsWith(end) }
      }

  val sourceToDestination = input.subList(startIndex + 1, endIndex - 1)

  return sourceToDestination.map { line ->
    val splitted = line.split(" ")
    SourceDestinationRange(splitted[1].toLong(), splitted[0].toLong(), splitted[2].toLong())
  }
}

fun findDestination(ranges: List<SourceDestinationRange>, seed: Long): Long {
  val seedRange = ranges.find { seed in it.sourceStart ..< (it.sourceStart + it.rangeLength) }
  return if (seedRange == null) {
    seed
  } else {
    seedRange.destinationStart + (seed - seedRange.sourceStart)
  }
}

fun main() {
  fun part1(input: List<String>): Long {
    val seeds = parseSeeds(input)
    val ranges =
        listOf(
            buildSourceToDestinationRanges(input, "seed-to-soil map:", "soil-to-fertilizer map:"),
            buildSourceToDestinationRanges(
                input, "soil-to-fertilizer map:", "fertilizer-to-water map:"),
            buildSourceToDestinationRanges(
                input, "fertilizer-to-water map:", "water-to-light map:"),
            buildSourceToDestinationRanges(
                input, "water-to-light map:", "light-to-temperature map:"),
            buildSourceToDestinationRanges(
                input, "light-to-temperature map:", "temperature-to-humidity map:"),
            buildSourceToDestinationRanges(
                input, "temperature-to-humidity map:", "humidity-to-location map:"),
            buildSourceToDestinationRanges(input, "humidity-to-location map:", ""))

    return seeds.minOf { seed -> goThroughSeedDestinations(ranges, seed) }
  }

  fun part2(input: List<String>): Long {
    val seedRanges = parseSeedsPart2(input)
    val ranges =
        listOf(
            buildSourceToDestinationRanges(input, "seed-to-soil map:", "soil-to-fertilizer map:"),
            buildSourceToDestinationRanges(
                input, "soil-to-fertilizer map:", "fertilizer-to-water map:"),
            buildSourceToDestinationRanges(
                input, "fertilizer-to-water map:", "water-to-light map:"),
            buildSourceToDestinationRanges(
                input, "water-to-light map:", "light-to-temperature map:"),
            buildSourceToDestinationRanges(
                input, "light-to-temperature map:", "temperature-to-humidity map:"),
            buildSourceToDestinationRanges(
                input, "temperature-to-humidity map:", "humidity-to-location map:"),
            buildSourceToDestinationRanges(input, "humidity-to-location map:", ""))
    return seedRanges.minOf { seedRange ->
      seedRange.minOf { seed -> goThroughSeedDestinations(ranges, seed) }
    }
  }

  val testInput = readInput("Day05_test")
  println(part1(testInput))
  check(part1(testInput) == 35L)

  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}
