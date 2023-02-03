import api.ChartItem
import api.ChartList
import api.ChartType
import api.LastFmApi


const val USERNAME = "iigne"
fun main() {
    val api = LastFmApi()
    val seasonsService = SeasonsService(api)
    val seasons = seasonsService.getAllSeasonsForUser(USERNAME)

    val albumChartsForSeasons = api.getChartsForSeasons(ChartType.ALBUM, USERNAME, seasons, 15)
    println(albumChartsForSeasons)

//    val trackChartsForSeasons = api.getChartsForSeasons(ChartType.TRACK, USERNAME, seasons, 5)
//    println(trackChartsForSeasons)

    val aggregated = aggregateYearlySeasonsChart(albumChartsForSeasons)
    println(aggregated)
}


private fun aggregateYearlySeasonsChart(yearlySeasonsChart: Map<YearSeason, ChartList>): Map<Season, List<ChartItem>> {
    val groupedBySeason: Map<Season, List<ChartList>> = yearlySeasonsChart.entries.groupBy {it.key.season}
        .mapValues { value -> value.value.map { it.value } }
    val aggregatedBySeason: MutableMap<Season, List<ChartItem>> = mutableMapOf()

    for (seasonListEntry in groupedBySeason) {
        val yearlySeasonChartsFlat = seasonListEntry.value.flatMap { it.list }
        val groupedById = yearlySeasonChartsFlat.groupBy { Pair(it.id, it.name) }.mapValues { value -> createChartItem(value.value[0], value.value.sumOf {it.playcount  }) }
        aggregatedBySeason[seasonListEntry.key] = groupedById.values.toList().sortedBy { it.playcount }
    }
    return aggregatedBySeason
}

private fun createChartItem(originalChartItem: ChartItem, totalPlayCount: Int): ChartItem =
    originalChartItem.apply {
        playcount = totalPlayCount
    }



