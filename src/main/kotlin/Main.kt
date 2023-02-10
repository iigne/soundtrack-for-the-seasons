import api.ChartType
import api.LastFmApi
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

//args expected format:
// 0 - username
// 1 - limit (optional)
const val DEFAULT_LIMIT = 15
val DEFAULT_TYPE = ChartType.ALBUM
fun main(args: Array<String>) {
    
    //resolve inputs
    val apiKey = getApiKey()
    val username = extractArg(args, 0)
    val limit = extractArg(args, 1, DEFAULT_LIMIT)
    val type = extractArg(args, 2, DEFAULT_TYPE)

    println("args: username $username, limit $limit, type $type")
    //init services
    val api = LastFmApi(apiKey)
    val seasonsService = SeasonsService(api)
    val seasonChartAggregationService = ChartAggregationService()

    // generate stats
    val seasons = seasonsService.getAllSeasonsForUser(username)

    val chart = api.getChartsForSeasons(type, username, seasons, limit)
    println("Every year seasons chart:")
    println(chart)

    val aggregated = seasonChartAggregationService.aggregateYearlySeasonsChart(chart, limit)
    println("Aggregated seasons chart:")
    //TODO mapper could be in some util class
    println(jacksonObjectMapper().writer().writeValueAsString(aggregated))
}

fun getApiKey(): String = System.getenv("LAST_FM_API_KEY")
    .also { require(it != null) { "Last.fm API key not provided" } }

fun extractArg(args: Array<String>, index: Int, defaultVal: String? = null): String {
    if (args.size - 1 >= index) {
        return args[index]
    }
    require(!defaultVal.isNullOrEmpty()) { "Arg at position $index is not present and no default value given"}
    return defaultVal
}

fun extractArg(args: Array<String>, index: Int): String =
    extractArg(args, index, "")

fun extractArg(args: Array<String>, index: Int, defaultVal: Int? = null): Int =
    extractArg(args, index, defaultVal.toString()).toInt()

fun extractArg(args: Array<String>, index: Int, defaultVal: ChartType? = null): ChartType =
    ChartType.valueOf(extractArg(args, index, defaultVal.toString()))