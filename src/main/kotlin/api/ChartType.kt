package api

enum class ChartType(val endpoint: Endpoint, val jsonWrapperRoot: String) {
    TRACK(Endpoint.USER_TRACK_CHART, "weeklytrackchart"),
    ALBUM(Endpoint.USER_ALBUM_CHART, "weeklyalbumchart"),
    ARTIST(Endpoint.USER_ARTIST_CHART, "weeklyartistchart")
}