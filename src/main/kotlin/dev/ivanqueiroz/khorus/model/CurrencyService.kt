package dev.ivanqueiroz.khorus.model

import dev.ivanqueiroz.khorus.exchangeapi.ExchangeRatesClient
import dev.ivanqueiroz.khorus.util.convert
import dev.ivanqueiroz.khorus.util.getLogger
import dev.ivanqueiroz.khorus.util.rateCalc
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@Service
class CurrencyService(val currencyRepository: CurrencyRepository, val exchangeRatesClient: ExchangeRatesClient) {

    companion object {
        private val log = getLogger(CurrencyService::class.java)
    }

    fun calculateConversion(amount: BigDecimal?, userId: Long, currencySource: String, currencyDestiny: String): Currency {
        val exchangeResponseOptional = exchangeRatesClient.callExchangeRatesApi(userId)
        var sourceRate = "0.0".toBigDecimal()
        var destinyRate = "0.0".toBigDecimal()

        exchangeResponseOptional.ifPresent {
            sourceRate = it.rates.getOrDefault(currencySource, "0.0".toBigDecimal())
            destinyRate= it.rates.getOrDefault(currencyDestiny, "0.0".toBigDecimal())
        }

        val currency = Currency(
            userId = userId,
            amount = amount,
            currencySource = currencySource,
            currencyDestiny = currencyDestiny,
            date = Date.from(ZonedDateTime.now(ZoneId.of("UTC")).toInstant()),
            result = amount?.let { convert(it, sourceRate, destinyRate) },
            currencyRate = rateCalc(sourceRate, destinyRate),
        )

        log.info("$currency saved and returned.")
        currencyRepository.save(currency)
        return currency

    }

    fun allTransactionsFromUser(userId: Long): List<Currency> {
        val allTransactions = currencyRepository.findByUserId(userId)
        log.info("UserId $userId requested all transactions. Found: ${allTransactions.size} records")
        return allTransactions
    }

}
