import java.time.LocalDateTime

data class YearSeason(
    val season: Season,
    val seasonStart: LocalDateTime,
    val seasonStartTimestamp: Long,
    val seasonEnd: LocalDateTime,
    val seasonEndTimestamp: Long,
)