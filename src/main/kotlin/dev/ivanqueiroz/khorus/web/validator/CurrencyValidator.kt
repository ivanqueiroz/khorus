package dev.ivanqueiroz.khorus.web.validator

import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class CurrencyValidator : ConstraintValidator<ValidCurrency, String> {

    override fun isValid(currency: String?, context: ConstraintValidatorContext?): Boolean {
        if (currency != null) {
            return if (Objects.nonNull(currency) && currency.isNotBlank() && currency.length == 3) {
                listOf(*"BRL,USD,EUR,JPY".split(",").toTypedArray()).contains(currency)
            } else false
        }
        return false
    }

}
