package dev.ivanqueiroz.khorus.web.dto

import dev.ivanqueiroz.khorus.web.validator.ValidCurrency
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.Digits
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class CurrencyDto(
    var transactionId: Long?,
    @field:NotNull(message = "User ID is mandatory.")
    @PositiveOrZero(message = "User ID is mandatory.")
    val userId: Long,
    @ValidCurrency
    @field:NotBlank(message = "Source currency is mandatory (Valid options: BRL, USD, EUR, JPY)")
    val currencySource: String,
    @NotNull(message = "Amount is mandatory.")
    @PositiveOrZero(message = "Amount is mandatory.")
    @Digits(integer = 6, fraction = 2, message = "Amount out of range.")
    val amount: BigDecimal,
    @field:NotBlank(message = "Destiny currency is mandatory (Valid options: BRL, USD, EUR, JPY)")
    val currencyDestiny: String,
    var result: BigDecimal?,
    var currencyRate: BigDecimal?,
    var date: Date?
)
