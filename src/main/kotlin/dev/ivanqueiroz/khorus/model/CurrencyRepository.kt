package dev.ivanqueiroz.khorus.model

import org.springframework.data.jpa.repository.JpaRepository

interface CurrencyRepository: JpaRepository<Currency, Long> {
    fun findByUserId(userId: Long): List<Currency>
}
