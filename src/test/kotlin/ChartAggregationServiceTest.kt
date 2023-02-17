import api.Artist
import api.ChartItem
import api.ChartList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ChartAggregationServiceTest {

    private val chartAggregationService = ChartAggregationService()

    @Test
    fun aggregateYearlySeasonsChart_empty() {
        val aggregatedChart = chartAggregationService.aggregateYearlySeasonsChart(mapOf())
        assertEquals(0, aggregatedChart.size)
    }

    @Test
    fun aggregateYearlySeasonsChart_allSeasons() {
        val aggregatedChart = chartAggregationService.aggregateYearlySeasonsChart(makeSeasonsChart(), 3)

        assertEquals(4, aggregatedChart.size)

        assertEquals(Season.SUMMER, aggregatedChart[0].season)
        with(aggregatedChart[0].list) {
            assertEquals(3, size)
            this[0].assertEquals(1010, "HAIM", "Days Are Gone", "0c44d4f7-c9cd-4f9d-8e64-4a6677f85145")
            this[1].assertEquals(150, "Another obscure artist", "Another obscure album", "")
            this[2].assertEquals(110, "Some obscure artist", "Obscure album", "")
        }

        assertEquals(Season.AUTUMN, aggregatedChart[1].season)
        with(aggregatedChart[1].list) {
            assertEquals(3, size)
            this[0].assertEquals(300, "John Frusciante", "Shadows Collide With People", "0c18d5dd-3e3d-459c-b647-80734819d072")
            this[1].assertEquals(51, "Red Hot Chili Peppers", "I'm With You", "1913928d-2516-4a0a-8095-9f9e5747fe58")
            this[2].assertEquals(15, "Radiohead", "OK Computer", "0b6b4ba0-d36f-47bd-b4ea-6a5b91842d29")
        }

        assertEquals(Season.WINTER, aggregatedChart[2].season)
        with(aggregatedChart[2].list) {
            assertEquals(2, size)
            this[0].assertEquals(354, "King Gizzard & The Lizard Wizard", "K.G", "48acfcf1-8114-44a9-b45b-f828a8fcce87")
            this[1].assertEquals(197, "Radiohead", "OK Computer", "0b6b4ba0-d36f-47bd-b4ea-6a5b91842d29")
        }

        assertEquals(Season.SPRING, aggregatedChart[3].season)
        assertEquals(0, aggregatedChart[3].list.size)
    }

    private fun makeSeasonsChart() =
        mapOf(
            makeYearSeason(Season.SUMMER, 2020) to ChartList(listOf(
                ChartItem(Artist("HAIM", "0c44d4f7-c9cd-4f9d-8e64-4a6677f85145"), "0c44d4f7-c9cd-4f9d-8e64-4a6677f85145", "Days Are Gone", 1000),
                ChartItem(Artist("Some obscure artist", ""), "", "Obscure album", 100),
            )),
            makeYearSeason(Season.AUTUMN, 2020) to ChartList(listOf(
                ChartItem(Artist("John Frusciante", "f1571db1-c672-4a54-a2cf-aaa329f26f0b"), "0c18d5dd-3e3d-459c-b647-80734819d072", "Shadows Collide With People", 150),
                ChartItem(Artist("Red Hot Chili Peppers", "8bfac288-ccc5-448d-9573-c33ea2aa5c30"), "1913928d-2516-4a0a-8095-9f9e5747fe58", "I'm With You", 25),
                ChartItem(Artist("Fake Hot Chili Peppers", "fffff"), "123", "I'm With You", 9),
                ChartItem(Artist("Radiohead", "a74b1b7f-71a5-4011-9441-d0b5e4122711"), "0b6b4ba0-d36f-47bd-b4ea-6a5b91842d29", "OK Computer", 15),
            )),
            makeYearSeason(Season.WINTER, 2020) to ChartList(listOf(
                ChartItem(Artist("Radiohead", "a74b1b7f-71a5-4011-9441-d0b5e4122711"), "0b6b4ba0-d36f-47bd-b4ea-6a5b91842d29", "OK Computer", 197),
                ChartItem(Artist("King Gizzard & The Lizard Wizard", "f58384a4-2ad2-4f24-89c5-c7b74ae1cce7"), "48acfcf1-8114-44a9-b45b-f828a8fcce87", "K.G", 354),
            )),
            makeYearSeason(Season.SPRING, 2021) to ChartList(listOf()),
            makeYearSeason(Season.SUMMER, 2021) to ChartList(listOf(
                ChartItem(Artist("HAIM", "0c44d4f7-c9cd-4f9d-8e64-4a6677f85145"), "0c44d4f7-c9cd-4f9d-8e64-4a6677f85145", "Days Are Gone", 10),
                ChartItem(Artist("John Frusciante", "f1571db1-c672-4a54-a2cf-aaa329f26f0b"), "0c18d5dd-3e3d-459c-b647-80734819d072", "Shadows Collide With People", 9),
                ChartItem(Artist("Some obscure artist", ""), "", "Obscure album", 10),
                ChartItem(Artist("Another obscure artist", ""), "", "Another obscure album", 150),
            )),
            makeYearSeason(Season.AUTUMN, 2021) to ChartList(listOf(
                ChartItem(Artist("John Frusciante", "f1571db1-c672-4a54-a2cf-aaa329f26f0b"), "0c18d5dd-3e3d-459c-b647-80734819d072", "Shadows Collide With People", 150),
                ChartItem(Artist("Red Hot Chili Peppers", "8bfac288-ccc5-448d-9573-c33ea2aa5c30"), "1913928d-2516-4a0a-8095-9f9e5747fe58", "I'm With You", 26)
            )),
        )

    //season start and end don't matter here
    private fun makeYearSeason(season: Season, year: Int): YearSeason =
        YearSeason(season, LocalDate.of(year, 1, 1).atStartOfDay(), 0L, LocalDate.of(year, 12, 31).atStartOfDay(), 0L)

    private fun AggregatedChartItem.assertEquals(playcount: Int, artist: String, name: String, id: String) {
        assertEquals(playcount, this.playcount)
        assertEquals(artist, this.artist)
        assertEquals(name, this.name)
        assertEquals(id, this.id)
    }
}