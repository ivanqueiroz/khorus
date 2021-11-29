package dev.ivanqueiroz.khorus.web.validator

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS)
@Constraint(validatedBy = [CurrencyValidator::class])
annotation class ValidCurrency(
    val message: String = "Currency value invalid. Permited values: BRL, USD, EUR, JPY",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<Payload>> = []
)
