package api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    val name: String,
    val playcount: Int,
    val registered: Registered,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Registered(
    @JsonProperty("unixtime")
    val timestamp: String
)
