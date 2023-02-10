import java.time.LocalDateTime
import java.time.Year

//TODO omg please refactor this class
data class YearSeason(
    val date: LocalDateTime,
    val season: Season = getSeasonForDate(date),
    val seasonStart: LocalDateTime = LocalDateTime.of(date.year, season.months[0].value, 1, 0, 0),
    val seasonStartTimestamp: Long = timestampToDate(seasonStart),
    val seasonEnd: LocalDateTime = LocalDateTime.of(getSeasonEndYear(date), season.months.last().value, season.months.last().length(Year.of(getSeasonEndYear(date)).isLeap), 23, 59, 59),
    val seasonEndTimestamp: Long = timestampToDate(seasonEnd),
)