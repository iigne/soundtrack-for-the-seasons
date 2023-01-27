package api

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

//Weekly chart both same for tracks and albums.
// Slightly different for artist, but that can be done another time
@JsonIgnoreProperties(ignoreUnknown = true)
data class ChartItem(
    val artist: Artist,
    @JsonProperty("mbid")
    val id: String,
    val name: String,
    val playcount: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChartList(
    @JsonProperty("track")
    @JsonAlias("album")
    val list: List<ChartItem>,
)