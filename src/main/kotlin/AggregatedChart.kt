data class AggregatedChart(
    val season: Season,
    val list: List<AggregatedChartItem>,
)

data class AggregatedChartItem(
    val id: String?,
    val artist: String?,
    val name: String,
    val playcount: Int
)