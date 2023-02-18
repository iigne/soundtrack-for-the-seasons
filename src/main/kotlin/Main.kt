import api.ChartType
import api.LastFmApiService
import java.time.Clock

//args description:
// 0 - username - of Last.fm user
// 1 - limit (optional) - the higher the limit, the more items will be fetched for generating the stats,
//      and more items will appear in the output list. Default limit is 15.
// 2 - type (optional) - type of the stats. Can be either ALBUM, TRACK or ARTIST. Default type is ALBUM.
const val DEFAULT_LIMIT = 15
val DEFAULT_TYPE = ChartType.ALBUM
fun main(args: Array<String>) {
    
    //resolve inputs
    val username = extractArg(args, 0)
    val limit = extractIntArg(args, 1, DEFAULT_LIMIT)
    val type = extractChartTypeArg(args, 2, DEFAULT_TYPE)

    println("args: username $username, limit $limit, type $type")
    //init services
    val api = LastFmApiService()
    val seasonsService = SeasonsService(api, Clock.systemDefaultZone())
    val seasonChartAggregationService = ChartAggregationService()

    // generate stats
    val userSeasons = seasonsService.getAllSeasonsForUser(username)

    val chart = api.getChartsForSeasons(type, username, userSeasons, limit)
    println("Every year seasons chart:")
    println(chart)

    val aggregated = seasonChartAggregationService.aggregateYearlySeasonsChart(chart, limit)
    println("Aggregated seasons chart:")
    println(aggregated.objectToJson())
}

fun extractArg(args: Array<String>, index: Int, defaultVal: String? = null): String {
    val extracted = extractArgOrNull(args, index) ?: defaultVal
    require(extracted != null) { "Arg at position $index is not present and no default value given" }
    return extracted
}

fun extractArgOrNull(args: Array<String>, index: Int): String? =
    if (args.size - 1 >= index) args[index] else null

fun extractIntArg(args: Array<String>, index: Int, defaultVal: Int? = null): Int =
    extractArg(args, index, defaultVal.toString()).toInt()

fun extractChartTypeArg(args: Array<String>, index: Int, defaultVal: ChartType? = null): ChartType =
    ChartType.valueOf(extractArg(args, index, defaultVal.toString()))
