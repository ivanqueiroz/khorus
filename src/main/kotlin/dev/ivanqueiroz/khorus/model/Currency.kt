package dev.ivanqueiroz.khorus.model

import dev.ivanqueiroz.khorus.web.dto.CurrencyDto
import org.hibernate.Hibernate
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Currency(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var userId: Long? = null,
    var currencySource: String? = null,
    var currencyDestiny: String? = null,
    var amount: BigDecimal? = null,
    var currencyRate: BigDecimal? = null,
    var result: BigDecimal? = null,
    var date: Date? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Currency

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , userId = $userId , currencySource = $currencySource , currencyDestiny = $currencyDestiny , amount = $amount , currencyRate = $currencyRate , result = $result , date = $date )"
    }

}
