import java.time.LocalDateTime
import java.time.Month

enum class Season(val months: List<Month>) {
    WINTER(listOf(Month.DECEMBER, Month.JANUARY, Month.FEBRUARY)),
    SPRING(listOf(Month.MARCH, Month.APRIL, Month.MAY)),
    SUMMER(listOf(Month.JUNE, Month.JULY, Month.AUGUST)),
    AUTUMN(listOf(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER)),
}

fun getSeasonForDate(date: LocalDateTime): Season {
    for (season in Season.values()) {
        if (date.month in season.months) return season
    }
    throw IllegalStateException("Illegal date $date does not belong to a season? 🤨")
}

fun getSeasonStartYear(date: LocalDateTime): Int {
    if (getSeasonForDate(date) == Season.SPRING) {
        return date.year + 1
    }
    return date.year
}

fun getSeasonEndYear(date: LocalDateTime): Int {
    if (getSeasonForDate(date) == Season.WINTER) {
        return date.year + 1
    }
    return date.year
}