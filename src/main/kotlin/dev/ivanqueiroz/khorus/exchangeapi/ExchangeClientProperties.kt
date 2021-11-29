package dev.ivanqueiroz.khorus.exchangeapi

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "exchange-rate-api")
class ExchangeClientProperties {
    var baseUrl: String = ""
    var apiKey: String = ""
    var format: String = ""
}
