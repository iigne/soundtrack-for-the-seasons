import api.ChartItem
import api.ChartList

class ChartAggregationService {

    //TODO should this really return a list? there's only ever 4 entries here
    fun aggregateYearlySeasonsChart(yearlySeasonsChart: Map<YearSeason, ChartList>, limit: Int = 15): List<AggregatedChart> {
        val yearlyChartsToSeason: Map<Season, List<ChartList>> = yearlySeasonsChart.entries
            .groupBy {it.key.season} //group all charts by season
            .mapValues { value -> value.value.map { it.value } } // flatten and extract only chart list
        return yearlyChartsToSeason.map { season ->
            AggregatedChart(season.key, aggregateYearlyData(season.value.flatMap { it.list })
                .take(limit)) //return only n items based on limit
        }
    }

    private fun aggregateYearlyData(allYearsCharts: List<ChartItem>): List<AggregatedChartItem> =
        allYearsCharts.groupBy { Pair(it.id, it.name) } // group each item by id and name (to handle where id is null)
            .mapValues { value -> createChartItem(value.value[0], value.value.sumOf {it.playcount  }) } //sum playcounts
            .values.toList().sortedByDescending { it.playcount }


    private fun createChartItem(originalChartItem: ChartItem, totalPlayCount: Int): AggregatedChartItem =
        AggregatedChartItem(originalChartItem.id, originalChartItem.artist?.name, originalChartItem.name, totalPlayCount)

}