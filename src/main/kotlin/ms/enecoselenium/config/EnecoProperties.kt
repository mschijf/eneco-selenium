package ms.enecoselenium.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "eneco")
data class EnecoProperties(
    val username: String,
    val password: String,
    val frequency_in_hours: Long
)
