import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun timestampToDate(timestamp: String, zoneId: ZoneId): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp.toLong() * 1000), zoneId)

fun dateToTimestamp(date: LocalDateTime, zoneId: ZoneId): Long =
    date.atZone(zoneId).toEpochSecond()
