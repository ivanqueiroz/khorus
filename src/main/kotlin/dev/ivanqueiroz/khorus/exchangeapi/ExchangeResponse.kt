package dev.ivanqueiroz.khorus.exchangeapi

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExchangeResponse( @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") var date: Date = Date()) {
    var timestamp: Long = 0L
    var success: Boolean = false
    var base: String = ""
    var rates: Map<String, BigDecimal> = emptyMap()
}
