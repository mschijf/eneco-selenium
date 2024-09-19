package ms.enecoselenium

import ms.enecoselenium.eneco.EnecoSelenium
import ms.enecoselenium.homemonitor.HomeMonitorUpdater
import kotlin.test.Ignore

class EnecoSeleniumTest {

    @Ignore
//    @Test
    fun scrapeEnecoPage() {

        println("Start scraping")
        val pageSource = EnecoSelenium().scrapeEnecoPage()
        println("finish scraping")
        if (pageSource == null) {
            println("error while scraping eneco page")
            return
        }

        val apiKey = getValueForKey(pageSource, "FE_DC_API_KEY")
        val accessToken = getValueForKey(pageSource, "accessToken")
        println("apiKey: $apiKey")
        println("accessToken: $accessToken")

        println("Start Updating")
        HomeMonitorUpdater().doTheUpdate(pageSource)
        println("Finished Updating")
    }


    private fun getValueForKey(htmlPage: String, key: String): String {
        if (htmlPage.contains(key)) {
            val value = htmlPage.substringAfter("\"$key\":").substringBefore(",").trim()
            return value.removeSurrounding("\"")
        } else {
            println("cannot found $key on htmlPage")
            return ""
        }
    }

}
