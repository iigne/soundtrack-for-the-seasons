import api.ChartType
import api.LastFmApi
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun main(args: Array<String>) {

    //resolve inputs
    val apiKey = getApiKey()
    val username = args[0]
    //TODO pass in limit through args

    //init services
    val api = LastFmApi(apiKey)
    val seasonsService = SeasonsService(api)
    val seasonChartAggregationService = ChartAggregationService()

    // generate stats
    val seasons = seasonsService.getAllSeasonsForUser(username)

    //TODO add args choice for which type we want to generate the thing for
    val chart = api.getChartsForSeasons(ChartType.ALBUM, username, seasons, 15)
    println("All seasons albums:")
    println(chart)
//
//    val chart = api.getChartsForSeasons(ChartType.TRACK, username, seasons, 15)
//    println("All seasons tracks:")
//    println(chart)

//    val chart = api.getChartsForSeasons(ChartType.ARTIST, username, seasons, 15)
//    println("All seasons artists:")
//    println(chart)

    val aggregated = seasonChartAggregationService.aggregateYearlySeasonsChart(chart, 15)
    println("Aggregated seasons chart:")
    //TODO mapper could be in some util class
    println(jacksonObjectMapper().writer().writeValueAsString(aggregated))
}

fun getApiKey(): String = System.getenv("LAST_FM_API_KEY")
    .also { require(it != null) { "Last.fm API key not provided" } }
