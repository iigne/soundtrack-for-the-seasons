import api.ChartType
import api.LastFmApi

fun main() {
    val api = LastFmApi()

    val currentTimestamp = System.currentTimeMillis()/1000
    val tracks = api.getChart(ChartType.TRACK, "iigne","1570760000", currentTimestamp.toString(), 20)
    println(tracks)
    val albums = api.getChart(ChartType.ALBUM, "iigne", "1570760000", currentTimestamp.toString(), 20)
    println(albums)

}


