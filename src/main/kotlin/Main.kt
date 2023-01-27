import api.LastFmApi

fun main() {
    val api = LastFmApi()
    val seasonsAggregator = SeasonsAggregator(api)
    seasonsAggregator.getAllSeasonsForUser("iigne")

}


