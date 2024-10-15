package ms.enecoselenium.service

import java.io.File
import java.time.LocalDateTime

class PersistedLocalDateTime (persistetFileName: String) {
    private val file = File(persistetFileName)

    fun set(time: LocalDateTime) {
        file.appendText(time.toString()+"\n")
    }

    fun get(): LocalDateTime {
        if (!file.exists() || file.length() == 0L) {
            return LocalDateTime.MIN
        }

        val timeStr = file.readLines().last()
        return LocalDateTime.parse(timeStr)
    }
}