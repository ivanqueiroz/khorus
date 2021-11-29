package dev.ivanqueiroz.khorus.exchangeapi

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigDecimal

internal class ExchangeRatesClientTest {

    private val mockWebServer = MockWebServer()
    private lateinit var exchangeRateClient: ExchangeRatesClient

    @BeforeEach
    fun setUp() {
        val properties = ExchangeClientProperties()
        properties.baseUrl = mockWebServer.url("/").toUrl().toString()
        properties.apiKey = "ba99491b4d18b57a0ebd5779f62d745c"
        properties.format = "1"
        exchangeRateClient = ExchangeRatesClient(WebClient.create(), properties)
    }

    @Test
    fun getRate() {
        val json = "{ \"success\":true, \"timestamp\":1631958243, \"base\":\"EUR\", \"date\":\"2021-09-18\", \"rates\":{ \"BRL\":6.201951, \"USD\":1.17259, \"EUR\":1, \"JPY\":128.901692 } }"

        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(json)
        )

        val expectedBrlRate = "128.901692".toBigDecimal()
        val rate = exchangeRateClient.getRate()
        assertEquals(expectedBrlRate, rate.orElse(ExchangeResponse()).rates["JPY"])
    }
}
