package api

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChartItem(
    val artist: Artist?,
    @JsonProperty("mbid")
    val id: String,
    val name: String,
    val playcount: Int,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ChartList(
    @JsonProperty("track")
    @JsonAlias("album", "artist")
    val list: List<ChartItem>,
)
data class Artist(
    @JsonProperty("#text")
    val name: String,
    @JsonProperty("mbid")
    val id: String,
)