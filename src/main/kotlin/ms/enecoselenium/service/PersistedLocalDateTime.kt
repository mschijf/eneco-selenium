package ms.enecoselenium.service

import java.io.File
import java.time.LocalDateTime

class PersistedLocalDateTime (persistetFileName: String) {
    private val file = File(persistetFileName)

    fun set(time: LocalDateTime) {
        file.writeText(time.toString())
    }

    fun get(): LocalDateTime {
        if (!file.exists() || file.length() == 0L) {
            return LocalDateTime.MIN
        }

        val timeStr = file.readText()
        return LocalDateTime.parse(timeStr)
    }
}