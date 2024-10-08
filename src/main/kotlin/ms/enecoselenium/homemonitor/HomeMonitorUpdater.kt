package ms.enecoselenium.homemonitor

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
//import okhttp3.*
//import okhttp3.MediaType.Companion.toMediaType
//import java.util.concurrent.TimeUnit




@Service
class HomeMonitorUpdater {

    private val log = LoggerFactory.getLogger(HomeMonitorUpdater::class.java)

    private fun containsKey(htmlPage: String, key: String): Boolean {
        return htmlPage.contains(key)
    }

    private fun pageSourceOk(pageSource: String): Boolean {
        val apiKeyFound = containsKey(pageSource, "FE_DC_API_KEY")
        val accessTokenFound = containsKey(pageSource, "accessToken")

        return (apiKeyFound && accessTokenFound)
    }

    fun doTheUpdate(postObject: String) {
        if (!pageSourceOk(postObject)) {
            log.error("Page source does not contain accessToken")
            return
        }

        val updatePersonUrl = "http://192.168.2.39:8080/eneco/update"

        val restTemplate = RestTemplate()
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        val request = HttpEntity<String>(postObject, headers)

        log.info("Sending eneco update details to home-monitor service")
        restTemplate.postForObject(updatePersonUrl, request, String::class.java)
        log.info("Request done")
    }
}