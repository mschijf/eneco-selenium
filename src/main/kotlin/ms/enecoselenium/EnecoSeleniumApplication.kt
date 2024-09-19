package ms.enecoselenium

import ms.enecoselenium.config.EnecoProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@Configuration
@EnableScheduling
@EnableConfigurationProperties(EnecoProperties::class)
class EnecoSeleniumApplication

fun main(args: Array<String>) {
	runApplication<EnecoSeleniumApplication>(*args)
}
