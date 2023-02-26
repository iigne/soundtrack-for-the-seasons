import api.LastFmApiService
import java.time.*

class SeasonsService(
    private val api: LastFmApiService,
    private val clock: Clock,
) {
    /**
     * Extract every season from every year that user has been active for
     */
    fun getAllSeasonsForUser(username: String): List<YearSeason> {
        val registeredTimestamp = api.getUser(username).registered.timestamp
        val registeredDate = timestampToDate(registeredTimestamp, clock.zone)
        val currentDate = LocalDateTime.ofInstant(clock.instant(), clock.zone)
        return getListOfSeasonsBetweenDates(registeredDate, currentDate)
    }

    //TODO refactor this
    private fun getListOfSeasonsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): List<YearSeason> {
        val seasons = mutableListOf<YearSeason>()
        var counter = startDate
        while (counter < endDate) {
            val season = createYearSeason(counter)
            seasons.add(season)
            counter = season.seasonEnd.plusDays(1)
        }
        return seasons
    }

    private fun createYearSeason(date: LocalDateTime): YearSeason {
        val season = getSeasonForDate(date)
        val seasonStart = getSeasonStart(season, date)
        val seasonEnd = getSeasonEnd(season, date)
        return YearSeason(
            season,
            seasonStart,
            dateToTimestamp(seasonStart, clock.zone),
            seasonEnd,
            dateToTimestamp(seasonEnd, clock.zone)
        )
    }

    private fun getSeasonStart(season: Season, date: LocalDateTime): LocalDateTime =
        LocalDate.of(getSeasonStartYear(date), season.months[0].value, 1).atStartOfDay()

    private fun getSeasonEnd(season: Season, date: LocalDateTime): LocalDateTime {
        val year = getSeasonEndYear(date)
        val month = season.months.last()
        return LocalDate.of(year, month.value, getLastDayOfMonth(year, month)).atTime(23, 59, 59)
    }

    private fun getLastDayOfMonth(year: Int, month: Month) = month.length(Year.of(year).isLeap)

    //TODO this logic feels dirty...
    private fun getSeasonStartYear(date: LocalDateTime): Int =
        with(date) { if (month == Month.FEBRUARY || month == Month.JANUARY) year - 1 else year }

    private fun getSeasonEndYear(date: LocalDateTime): Int =
        with(date) { if (month == Month.DECEMBER) year + 1 else year }

}