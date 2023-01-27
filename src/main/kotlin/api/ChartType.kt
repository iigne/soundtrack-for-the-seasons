package api

enum class ChartType(val method: String, val jsonWrapperRoot: String) {
    TRACK("user.getWeeklyTrackChart", "weeklytrackchart"),
    ALBUM("user.getWeeklyAlbumChart", "weeklyalbumchart"),
//    ARTIST("user.getWeeklyArtistChart", "weeklyartistchart")
}