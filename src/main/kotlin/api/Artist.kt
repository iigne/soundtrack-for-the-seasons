package api

import com.fasterxml.jackson.annotation.JsonProperty

data class Artist(
    @JsonProperty("#text")
    val name: String,
    @JsonProperty("mbid")
    val id: String,
)
