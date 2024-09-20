package ms.enecoselenium.service

import ms.enecoselenium.eneco.EnecoSelenium
import ms.enecoselenium.homemonitor.HomeMonitorUpdater
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class SchedulerService (
    private val enecoSelenium: EnecoSelenium,
    private val homeMonitorUpdater: HomeMonitorUpdater): CommandLineRunner {

    private val log = LoggerFactory.getLogger(SchedulerService::class.java)
    private val lastTimeDone = PersistedLocalDateTime()

    init{
        log.info("Last time Eneco data has been read is ${lastTimeDone.get()}")
    }

//    @Scheduled(fixedRate = 60_000)
    private fun executeUpdate() {
        val now = LocalDateTime.now()
        if (now.minusMinutes(6*60).isAfter(lastTimeDone.get())) {
            log.info("Last update was ${lastTimeDone.get()}, starting new one")
            lastTimeDone.set(now)
            val pageSource = enecoSelenium.scrapeEnecoPage()
            if (pageSource != null) {
                homeMonitorUpdater.doTheUpdate(pageSource)
            } else {
                log.error("Empty SourcePage result from Eneco")
            }
        } else {
            log.info("Last update was recently (${lastTimeDone.get()}). Therefore no new update is done")
        }
    }

    override fun run(vararg args: String?) {
        executeUpdate()
    }
}