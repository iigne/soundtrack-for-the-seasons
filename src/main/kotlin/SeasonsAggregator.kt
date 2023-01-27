import api.LastFmApi
import java.time.LocalDateTime

class SeasonsAggregator (
    val api: LastFmApi
){
    /**
     * Extract every season from every year that user has been active for
     */
    fun getAllSeasonsForUser(username: String) {
        val registeredTimestamp = api.getUser(username).registered.timestamp
        val registeredDate = dateToTimestamp(registeredTimestamp)
        val list = getListOfSeasonsBetweenDates(registeredDate, LocalDateTime.now())
        print(list)
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