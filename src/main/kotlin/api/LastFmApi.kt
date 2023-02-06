package api

import YearSeason
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.Thread.sleep
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val baseUrl = "http://ws.audioscrobbler.com/2.0"

class LastFmApi(
    val apiKey: String,
) {

    companion object {
        const val RATE_LIMIT_MS = 1000L
    }

    fun getUser(username: String): User {
        val uri = buildUri(Endpoint.USER, username)
        val response = callApi(uri)

        val mapper = jacksonObjectMapper().reader().withRootName("user")
        return mapper.readValue(response.body(), User::class.java)
    }

    fun getChart(type: ChartType, username: String, from: String?, to: String?, limit: Int? = 15): ChartList {
        val uri = buildUri(type.endpoint, username, from, to, limit)
        val response = callApi(uri)
        val mapper = jacksonObjectMapper().reader().withRootName(type.jsonWrapperRoot)
        return mapper.readValue(response.body(), ChartList::class.java)
    }

    fun getChartsForSeasons(type: ChartType, username: String, seasons: List<YearSeason>, limit: Int? = 15):
            Map<YearSeason, ChartList> {
        val seasonsCharts: MutableMap<YearSeason, ChartList> = mutableMapOf()
        seasons.forEach {
            sleep(RATE_LIMIT_MS)
            seasonsCharts[it] =
                getChart(type, username, it.seasonStartTimestamp.toString(), it.seasonEndTimestamp.toString(), limit)
        }
        return seasonsCharts
    }

    private fun callApi(uri: URI): HttpResponse<String> {
        //TODO need a proper logger for this, this is more like debug log
        println("Calling URI: $uri")
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        require(response.statusCode() == 200) { "Last.fm API did not return 200" }
        println("${response.statusCode()}: ${response.body()}")
        return response
    }

    fun buildUri(endpoint: Endpoint, username: String, from: String? = null, to: String? = null, limit: Int? = null): URI =
        URI.create("""
            $baseUrl/?
            method=${endpoint.method}&
            user=$username&
            api_key=$apiKey&
            format=json&
            ${"from=$from&".useIf(from != null)}
            ${"to=$to&".useIf(to != null)}
            ${"limit=$limit&".useIf(limit != null)}
        """.trimIndent().replace("\n", "")
        )

    private fun String.useIf(condition: Boolean) = if (condition) this else ""

}