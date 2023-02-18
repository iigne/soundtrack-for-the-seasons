import api.LastFmApiService
import java.time.Clock
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class SeasonsService (
    private val api: LastFmApiService,
    private val clock: Clock,
){
    /**
     * Extract every season from every year that user has been active for
     */
    fun getAllSeasonsForUser(username: String): List<YearSeason> {
        val registeredTimestamp = api.getUser(username).registered.timestamp
        val registeredDate = timestampToDate(registeredTimestamp, clock.zone)
        val currentDate = LocalDateTime.ofInstant(clock.instant(), clock.zone)
        return getListOfSeasonsBetweenDates(registeredDate, currentDate)
    }


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
        val seasonStart = LocalDateTime.of(getSeasonStartYear(date), season.months[0].value, 1, 0, 0)
        val seasonEndYear = getSeasonEndYear(date)
        val seasonEnd = LocalDateTime.of(seasonEndYear, season.months.last().value, season.months.last().length(
            Year.of(seasonEndYear).isLeap), 23, 59, 59)
        return YearSeason(season, seasonStart, dateToTimestamp(seasonStart, clock.zone), seasonEnd, dateToTimestamp(seasonEnd, clock.zone))
    }

    private fun getSeasonStartYear(date: LocalDateTime): Int {
        if (date.month == Month.FEBRUARY || date.month == Month.JANUARY) {
            return date.year - 1
        }
        return date.year
    }

    private fun getSeasonEndYear(date: LocalDateTime): Int {
        if (date.month == Month.DECEMBER) {
            return date.year + 1
        }
        return date.year
    }

}