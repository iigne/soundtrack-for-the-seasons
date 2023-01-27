import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun dateToTimestamp(timestamp: String): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.toLong() * 1000), ZoneId.systemDefault())

fun timestampToDate(date: LocalDateTime): Long =
    date.atZone(ZoneId.systemDefault()).toEpochSecond()
