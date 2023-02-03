import api.ChartItem
import api.LastFmApi
import java.time.LocalDateTime

class SeasonsService (
    val api: LastFmApi
){
    /**
     * Extract every season from every year that user has been active for
     */
    fun getAllSeasonsForUser(username: String): List<YearSeason> {
        val registeredTimestamp = api.getUser(username).registered.timestamp
        val registeredDate = dateToTimestamp(registeredTimestamp)
        return getListOfSeasonsBetweenDates(registeredDate, LocalDateTime.now())
    }


    private fun getListOfSeasonsBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime): List<YearSeason> {
        val seasons = mutableListOf<YearSeason>()
        var counter = startDate
        while (counter < endDate) {
            val season = YearSeason(counter)
            seasons.add(season)
            counter = season.seasonEnd.plusDays(1)
        }
        return seasons
    }


}