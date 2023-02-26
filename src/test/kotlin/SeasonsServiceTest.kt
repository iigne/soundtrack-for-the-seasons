import api.LastFmApiService
import api.Registered
import api.User
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.test.assertEquals

class SeasonsServiceTest {

    companion object {
        private val FIXED_DATE: LocalDateTime = LocalDate.of(2023, 2, 16).atStartOfDay()
        private val ZONE_ID = ZoneId.of("UTC")
        private const val TEST_USERNAME = "test_user"
    }

    private val lastFmApiService = mockk<LastFmApiService>()
    private val fixedClock = Clock.fixed(FIXED_DATE.atZone(ZONE_ID).toInstant(), ZONE_ID)
    private val seasonsService = SeasonsService(lastFmApiService, fixedClock)

    @TestFactory
    fun `Get all seasons for user`() = listOf(
        makeDate(2023, 2, 15) to 1,
        makeDate(2022, 12, 1) to 1,
        makeDate(2022, 11, 30, 23) to 2,
        makeDate(2022, 10, 3) to 2,
        makeDate(2022, 9, 1) to 2,
        makeDate(2022, 8, 29) to 3,
        makeDate(2022, 6, 1) to 3,
        makeDate(2022, 5, 31, 23, 0) to 4,
        makeDate(2022, 4, 20) to 4,
        makeDate(2022, 3, 1) to 4,
        makeDate(2022, 2, 28) to 5,
        makeDate(2022, 1, 1) to 5,
        makeDate(2021, 12, 31) to 5,
        makeDate(2021, 11, 15) to 6,
        makeDate(2021, 3, 1) to 8,
        makeDate(2021, 2, 16) to 9,
        makeDate(2021, 1, 1) to 9,
    ).map { (date, expectedNumberSeasons) ->
        DynamicTest.dynamicTest("Should return $expectedNumberSeasons when user's join date is $date") {
            every { lastFmApiService.getUser(TEST_USERNAME) } returns User(TEST_USERNAME, 1, makeRegistered(date))
            val seasons = seasonsService.getAllSeasonsForUser(TEST_USERNAME)
            assertEquals(expectedNumberSeasons, seasons.size)
        }
    }

    @Test
    fun getAllSeasonsForUser_allProperties() {
        //given
        val expectedSeasons = listOf(
            YearSeason(Season.AUTUMN, makeDate(2019, 9, 1), 1567296000L, makeDate(2019, 11, 30, 23, 59, 59), 1575158399L),
            YearSeason(Season.WINTER, makeDate(2019, 12, 1), 1575158400L, makeDate(2020, 2, 29, 23, 59, 59), 1583020799L),
            YearSeason(Season.SPRING, makeDate(2020, 3, 1), 1583020800L, makeDate(2020, 5, 31, 23, 59, 59), 1590969599L),
            YearSeason(Season.SUMMER, makeDate(2020, 6, 1), 1590969600L, makeDate(2020, 8, 31, 23, 59, 59), 1598918399L),

            YearSeason(Season.AUTUMN, makeDate(2020, 9, 1), 1598918400L, makeDate(2020, 11, 30, 23, 59, 59), 1606780799L),
            YearSeason(Season.WINTER, makeDate(2020, 12, 1), 1606780800L, makeDate(2021, 2, 28, 23, 59, 59), 1614556799L),
            YearSeason(Season.SPRING, makeDate(2021, 3, 1), 1614556800L, makeDate(2021, 5, 31, 23, 59, 59), 1622505599L),
            YearSeason(Season.SUMMER, makeDate(2021, 6, 1), 1622505600L, makeDate(2021, 8, 31, 23, 59, 59), 1630454399L),

            YearSeason(Season.AUTUMN, makeDate(2021, 9, 1), 1630454400L, makeDate(2021, 11, 30, 23, 59, 59), 1638316799L),
            YearSeason(Season.WINTER, makeDate(2021, 12, 1), 1638316800L, makeDate(2022, 2, 28, 23, 59, 59), 1646092799L),
            YearSeason(Season.SPRING, makeDate(2022, 3, 1), 1646092800L, makeDate(2022, 5, 31, 23, 59, 59), 1654041599L),
            YearSeason(Season.SUMMER, makeDate(2022, 6, 1), 1654041600L, makeDate(2022, 8, 31, 23, 59, 59), 1661990399L),

            YearSeason(Season.AUTUMN, makeDate(2022, 9, 1), 1661990400L, makeDate(2022, 11, 30, 23, 59, 59), 1669852799L),
            YearSeason(Season.WINTER, makeDate(2022, 12, 1), 1669852800L, makeDate(2023, 2, 28, 23, 59, 59), 1677628799L),
        )

        //when
        val registeredDate = makeDate(2019, 10, 20)
        every { lastFmApiService.getUser(TEST_USERNAME) } returns User(TEST_USERNAME, 100000, makeRegistered(registeredDate))
        val seasons = seasonsService.getAllSeasonsForUser(TEST_USERNAME)

        //then
        assertEquals(expectedSeasons.size, seasons.size)
        seasons.forEachIndexed { index, it ->
            assertEquals(expectedSeasons[index].season, it.season)
            assertEquals(expectedSeasons[index].seasonStart, it.seasonStart)
            assertEquals(expectedSeasons[index].seasonStartTimestamp, it.seasonStartTimestamp)
            assertEquals(expectedSeasons[index].seasonEnd, it.seasonEnd)
            assertEquals(expectedSeasons[index].seasonEndTimestamp, it.seasonEndTimestamp)
        }
    }

    private fun makeRegistered(date: LocalDateTime) = Registered(dateToTimestamp(date, fixedClock.zone).toString())

    private fun makeDate(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): LocalDateTime =
        LocalDateTime.of(year, month, day, hour, minute, second)

}