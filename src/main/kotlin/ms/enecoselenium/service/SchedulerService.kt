package ms.enecoselenium.service

import ms.enecoselenium.config.EnecoProperties
import ms.enecoselenium.eneco.EnecoSelenium
import ms.enecoselenium.homemonitor.HomeMonitorUpdater
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class SchedulerService (
    private val enecoSelenium: EnecoSelenium,
    private val homeMonitorUpdater: HomeMonitorUpdater,
    private val enecoProperties: EnecoProperties): CommandLineRunner {

    private val log = LoggerFactory.getLogger(SchedulerService::class.java)
    private val nextTimeToScrape = PersistedLocalDateTime("next_time")

    init{
        log.info("Next time Eneco data will be scraped is ${nextTimeToScrape.get()}")
    }

    @Scheduled(fixedRate = 60*1_000)
    private fun executeUpdate() {
        val now = LocalDateTime.now()
        if (now.isAfter(nextTimeToScrape.get())) {
            if (homeMonitorUpdater.isReachable()) {
                log.info("Starting new update from Eneco")
                val pageSource = enecoSelenium.scrapeEnecoPage()
                if (pageSource != null && homeMonitorUpdater.pageSourceOk(pageSource)) {
                    homeMonitorUpdater.doTheUpdate(pageSource)
                    nextTimeToScrape.set(now.plusHours(enecoProperties.frequency_in_hours))
                } else {
                    log.error("Empty SourcePage result from Eneco or sourcePage not correct, try again after 15 minutes")
                    nextTimeToScrape.set(now.plusMinutes(15))
                }
            } else {
                logInfoDensed("Cannot reach the home-monitor service --> new update tried after 1 minute")
            }
        }
    }

    private var counter = 15
    private fun logInfoDensed(msg: String) {
        counter--
        if (counter == 0) {
            log.info(msg)
            counter = 15
        }
    }

    override fun run(vararg args: String?) {
//        executeUpdate()
    }
}