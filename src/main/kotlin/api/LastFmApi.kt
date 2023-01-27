package api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val apiKey = "ed8930baf8d046bb022e0059880ca274"
private const val baseUrl = "http://ws.audioscrobbler.com/2.0"

private const val userEndpoint = "user.getInfo"

class LastFmApi {

    fun getUser(username: String): User {
        val uri = buildUri(userEndpoint, username)
        val response = callApi(uri)

        val mapper = jacksonObjectMapper().reader().withRootName("user")
        return mapper.readValue(response.body(), User::class.java)
    }

    fun getChart(type: ChartType, username: String, from: String?, to: String?, limit: Int? = null): List<ChartItem> {
        val uri = buildUri(type.method, username, from, to, limit)
        val response = callApi(uri)
        val mapper = jacksonObjectMapper().reader().withRootName(type.jsonWrapperRoot)
        return mapper.readValue(response.body(), ChartList::class.java).list
    }

    fun callApi(uri: URI): HttpResponse<String> {
        println("URI: $uri")
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        require(response.statusCode() == 200) { "Last.fm API did not return 200"}
        println("${response.statusCode()}: ${response.body()}")
        return response
    }

    fun buildUri(method: String, username: String, from: String? = null, to: String? = null, limit: Int? = null): URI =
        URI.create("""
        $baseUrl/?
        method=$method&
        user=$username&
        api_key=$apiKey&
        format=json&
        ${"from=$from&".useIf(from != null)}
        ${"to=$to&".useIf(to != null)}
        ${"limit=$limit&".useIf(limit != null)}
    """.trimIndent().replace("\n", ""))

    private fun String.useIf(condition: Boolean) = if(condition) this else ""

}