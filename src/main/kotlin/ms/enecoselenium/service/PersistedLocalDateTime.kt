package ms.enecoselenium.service

import java.io.File
import java.time.LocalDateTime

class PersistedLocalDateTime {
    private val file = File("last_time")

    fun set(lastTime: LocalDateTime) {
        file.writeText(lastTime.toString())
    }

    fun get(): LocalDateTime {
        if (!file.exists() || file.length() == 0L) {
            return LocalDateTime.MIN
        }

        val lastTimeStr = file.readText()
        return LocalDateTime.parse(lastTimeStr)
    }
}