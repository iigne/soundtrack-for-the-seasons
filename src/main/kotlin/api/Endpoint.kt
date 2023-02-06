package api

enum class Endpoint(val method: String) {
    USER("user.getInfo"),
    USER_TRACK_CHART("user.getWeeklyTrackChart"),
    USER_ALBUM_CHART("user.getWeeklyAlbumChart"),
    USER_ARTIST_CHART("user.getWeeklyArtistChart"),
}