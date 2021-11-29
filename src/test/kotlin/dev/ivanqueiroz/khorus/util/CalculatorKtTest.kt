package dev.ivanqueiroz.khorus.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class CalculatorKtTest {

    @Test
    fun convertTest() {
        val expectedJpn = BigDecimal("103.920277")
        val sourceRateBrl = BigDecimal("6.201951")
        val destinyRateJpn = BigDecimal("128.901692")
        val amount = BigDecimal("5")
        val resultJpn = convert(amount, sourceRateBrl, destinyRateJpn)
        assertEquals(expectedJpn, resultJpn)
    }

    @Test
    fun rateCalcTest() {
        val sourceRateBrl = BigDecimal("6.201951")
        val destinyRateJpn = BigDecimal("128.901692")
        val resultJpn = rateCalc(sourceRateBrl, destinyRateJpn)
        val expectedJpnRate = BigDecimal("20.784056")
        assertEquals(expectedJpnRate, resultJpn)
    }
}
