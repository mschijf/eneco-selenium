package ms.enecoselenium.homemonitor

import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

//import okhttp3.*
//import okhttp3.MediaType.Companion.toMediaType
//import java.util.concurrent.TimeUnit




@Service
class HomeMonitorUpdater {

    private val log = LoggerFactory.getLogger(HomeMonitorUpdater::class.java)
    val restTemplate = RestTemplate()

    private fun containsKey(htmlPage: String, key: String): Boolean {
        return htmlPage.contains(key)
    }

    fun pageSourceOk(pageSource: String): Boolean {
        val apiKeyFound = containsKey(pageSource, "FE_DC_API_KEY")
        val accessTokenFound = containsKey(pageSource, "accessToken")

        return (apiKeyFound && accessTokenFound)
    }

    fun doTheUpdate(postObject: String) {
        val updateEnecoUrl = "http://192.168.2.39:8080/eneco/update"

        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        val request = HttpEntity<String>(postObject, headers)

        log.info("Sending eneco update details to home-monitor service")
        restTemplate.postForObject(updateEnecoUrl, request, String::class.java)
        log.info("Request done")
    }

    fun isReachable(): Boolean {
        val pingUrl = "http://192.168.2.39:8080/ping"

        try {
            val response = restTemplate.getForEntity(pingUrl, String::class.java)
            return response.statusCode.is2xxSuccessful
        } catch (e: RestClientException) {
            return false
        }
    }
}