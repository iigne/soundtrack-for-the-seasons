package api

import YearSeason
import jsonToObject

class LastFmApiService(
    private val lastFmApiHttpClient: LastFmApiHttpClient = LastFmApiHttpClient()
) {

    fun getUser(username: String): User {
        val uri = lastFmApiHttpClient.buildUri(Endpoint.USER, username)
        val response = lastFmApiHttpClient.callApiRateLimited(uri)
        return response.body().jsonToObject(User::class.java, "user")
    }

    private fun getChart(type: ChartType, username: String, from: String?, to: String?, limit: Int? = 15): ChartList {
        val uri = lastFmApiHttpClient.buildUri(type.endpoint, username, from, to, limit)
        val response = lastFmApiHttpClient.callApiRateLimited(uri)
        return response.body().jsonToObject(ChartList::class.java, type.jsonWrapperRoot)
    }

    fun getChartsForSeasons(type: ChartType, username: String, seasons: List<YearSeason>, limit: Int? = 15):
            Map<YearSeason, ChartList> = seasons.associateWith {
            getChart(type, username, it.seasonStartTimestamp.toString(), it.seasonEndTimestamp.toString(), limit)
        }

}