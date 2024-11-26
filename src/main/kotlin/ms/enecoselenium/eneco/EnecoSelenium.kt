package ms.enecoselenium.eneco

import io.github.bonigarcia.wdm.WebDriverManager
import ms.enecoselenium.config.EnecoProperties
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class EnecoSelenium(
    val enecoProperties: EnecoProperties) {

    private val log = LoggerFactory.getLogger(EnecoSelenium::class.java)

    fun scrapeEnecoPage():String? {
        try {

            log.info("start reading new Eneco data")


            // Set the path to GeckoDriver
            WebDriverManager.firefoxdriver().setup()
//          System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver")

            // Configure FirefoxOptions
            val options = FirefoxOptions()
            options.addArguments("--headless=new")
            options.addArguments("--no-sandbox")
            options.addArguments("--headless")

            // Initialize FirefoxDriver
            val driver: WebDriver = FirefoxDriver(options)

            val url = "https://inloggen.eneco.nl/"

            driver.get(url)
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20_000))

            Thread.sleep(3_000)
            val inlog = driver.findElement(By.name("identifier"))

            Thread.sleep(1_000)
            inlog.sendKeys(enecoProperties.username)
            inlog.submit()

            Thread.sleep(3_000)
            val pw = driver.findElement(By.name("credentials.passcode"))

            Thread.sleep(1_000)
            pw.sendKeys(enecoProperties.password)
            pw.submit()

            Thread.sleep(5_000)

            val pageSource = driver.pageSource

            driver.quit()

            log.info("Finish reading new Eneco data")
            return pageSource

        } catch (e: Exception) {
            log.error("Some error occurred", e)
            return null
        }
    }
}