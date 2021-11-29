package dev.ivanqueiroz.khorus.util

import java.math.BigDecimal
import java.math.RoundingMode

private val zero = "0.0".toBigDecimal()

fun convert(value: BigDecimal, sourceRate: BigDecimal, destinyRate: BigDecimal): BigDecimal {
    return if (sourceRate != zero && destinyRate != zero) value.multiply(destinyRate).divide(sourceRate, 6, RoundingMode.UP) else return zero
}

fun rateCalc(sourceRate: BigDecimal, destinyRate: BigDecimal): BigDecimal {
    return if (sourceRate != zero && destinyRate != zero) destinyRate.divide(sourceRate, 6, RoundingMode.UP) else return zero
}
