package dev.ivanqueiroz.khorus.exchangeapi

import lombok.extern.slf4j.Slf4j
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Slf4j
@Component
class ExchangeRatesClient(
    private val webClient: WebClient,
    private val exchangeClientProperties: ExchangeClientProperties
) {

    @Cacheable("rates")
    fun callExchangeRatesApi(userId: Long): Optional<ExchangeResponse> {
        return if (userId > 0) this.getRate() else Optional.empty()
    }

    fun getRate(): Optional<ExchangeResponse> {
        val baseUrl: String = exchangeClientProperties.baseUrl
        val apiKey: String = exchangeClientProperties.apiKey
        val format: String = exchangeClientProperties.format
        return webClient.get()
            .uri("$baseUrl/latest?access_key={apiKey}&symbols={listCurrency}&format={format}", apiKey, "BRL,USD,EUR,JPY", format)
            .retrieve()
            .bodyToMono(ExchangeResponse::class.java)
            .blockOptional()
    }
}
