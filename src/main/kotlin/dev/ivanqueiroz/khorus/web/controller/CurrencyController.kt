package dev.ivanqueiroz.khorus.web.controller

import dev.ivanqueiroz.khorus.model.CurrencyService
import dev.ivanqueiroz.khorus.util.getLogger
import dev.ivanqueiroz.khorus.web.dto.CurrencyDto
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

private const val NONE = "<none>"

@Api(tags = ["Application Operations"])
@RestController
@RequestMapping(path = ["/currency"])
class CurrencyController(val currencyService: CurrencyService) {

    companion object {
        private val log = getLogger(CurrencyController::class.java)
    }

    @ResponseBody
    @GetMapping(value = ["/convert"])
    @ApiResponses(
        value = [ApiResponse(code = 200, message = "Return currency transaction data."), ApiResponse(code = 400, message = "You don't have permission to use this resource."), ApiResponse(
            code = 500,
            message = "Internal exception."
        )]
    )
    fun convert(@Valid currencyDto: CurrencyDto): CurrencyDto {
        val currency = currencyService.calculateConversion(currencyDto.amount, currencyDto.userId, currencyDto.currencySource, currencyDto.currencyDestiny)
        return currencyDto.copy(
            transactionId = currency.id ?: 0L,
            currencySource = currency.currencySource ?: NONE,
            currencyDestiny = currency.currencyDestiny ?: NONE,
            date = currency.date ?: Date(),
            result = currency.result ?: "0.0".toBigDecimal(),
            currencyRate = currency.currencyRate ?: "0.0".toBigDecimal()
        ).also {
            if (log.isDebugEnabled) {
                log.debug("Currency $currency converted to dto $it.")
            }
        }


    }

    @GetMapping("/transactions")
    @ApiOperation(value = "Return a list with 0 o more transactions of informed user id.")
    @ApiResponses(
        value = [ApiResponse(code = 200, message = "Return transaction list of user id"), ApiResponse(code = 400, message = "You dont haver permission to use this resource."), ApiResponse(
            code = 500,
            message = "Internal exception."
        )]
    )
    fun getAllTransactions(@RequestParam @ApiParam(value = "User identification.", example = "1") userId: Long): List<CurrencyDto> {
        return currencyService.allTransactionsFromUser(userId).map {
            CurrencyDto(
                transactionId = it.id ?: 0L,
                currencySource = it.currencySource ?: NONE,
                currencyDestiny = it.currencyDestiny ?: NONE,
                date = it.date ?: Date(),
                result = it.result ?: "0.0".toBigDecimal(),
                currencyRate = it.currencyRate ?: "0.0".toBigDecimal(),
                amount = it.amount ?: "0.0".toBigDecimal(),
                userId = it.userId ?: 0L
            )
        }.also {
            log.info("Method allTransactions called with parameter $userId")
        }
    }


}
