import api.ChartItem
import api.ChartList

class ChartAggregationService {

    fun aggregateYearlySeasonsChart(yearlySeasonsChart: Map<YearSeason, ChartList>, limit: Int = 15): Map<Season, List<AggregatedChartItem>> {
        val yearlyChartsToSeason: Map<Season, List<ChartList>> = yearlySeasonsChart.entries
            .groupBy {it.key.season} //group all charts by season
            .mapValues { value -> value.value.map { it.value } } // flatten and extract only chart list
        return yearlyChartsToSeason.map { (season, yearCharts ) ->
            season to aggregateYearlyData(yearCharts.flatMap { it.list }).take(limit) //return only n items based on limit
        }.toMap()
    }


    private fun aggregateYearlyData(allYearsCharts: List<ChartItem>): List<AggregatedChartItem> =
    allYearsCharts.groupBy { Pair(it.id, it.name) } // group each item by id and name (to handle where id is null)
        .mapValues { value -> createChartItem(value.value[0], value.value.sumOf {it.playcount  }) } //sum playcounts
        .values.toList().sortedByDescending { it.playcount }


    private fun createChartItem(originalChartItem: ChartItem, totalPlayCount: Int): AggregatedChartItem =
        AggregatedChartItem(originalChartItem.id, originalChartItem.artist?.name, originalChartItem.name, totalPlayCount)

}