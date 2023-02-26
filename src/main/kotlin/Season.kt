import java.time.LocalDateTime
import java.time.Month

enum class Season(val months: List<Month>) {
    WINTER(listOf(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)),
    SPRING(listOf(Month.MARCH, Month.APRIL, Month.MAY)),
    SUMMER(listOf(Month.JUNE, Month.JULY, Month.AUGUST)),
    AUTUMN(listOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER)),
}

fun getSeasonForDate(date: LocalDateTime): Season =
    Season.values().find { date.month in it.months }
        ?: throw IllegalStateException("Illegal date $date does not belong to a season? 🤨")
