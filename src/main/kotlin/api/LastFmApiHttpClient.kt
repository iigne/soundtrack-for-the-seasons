package api

import java.lang.Thread.sleep
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class LastFmApiHttpClient(
    private val apiKey: String = System.getenv("LAST_FM_API_KEY").also { require(it != null) { "Last.fm API key not provided" } },
    private val httpClient: HttpClient = HttpClient.newBuilder().build()
) {

    companion object {
        const val RATE_LIMIT_MS = 1000L
        private const val baseUrl = "http://ws.audioscrobbler.com/2.0"
    }

    //TODO there should be some better way of dealing with rate limit...
    fun callApiRateLimited(uri: URI): HttpResponse<String> =
        callApi(uri).also { sleep(RATE_LIMIT_MS) }

    private fun callApi(uri: URI): HttpResponse<String> {
        //TODO need a proper logger for this
        println("Calling URI: $uri")
        val request = HttpRequest.newBuilder()
            .uri(uri)
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        println("${response.statusCode()}: ${response.body()}")
        require(response.statusCode() == 200) { "Last.fm API did not return 200" }
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