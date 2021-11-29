package dev.ivanqueiroz.khorus.web.controller

import dev.ivanqueiroz.khorus.web.dto.ApiErrorDto
import dev.ivanqueiroz.khorus.web.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(Throwable::class)
    fun handlerMethodThrowable(ex: Throwable): ResponseEntity<ApiErrorDto>{
        val errors: Set<ErrorDto> = mutableSetOf(ErrorDto(code = "Unknown Error", message = ex.message))
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseErrorBuilder(HttpStatus.INTERNAL_SERVER_ERROR, errors))
    }

    @ExceptionHandler(BindException::class)
    fun handleValidationExceptions(ex: BindException): ResponseEntity<ApiErrorDto> {
        val errors: Set<ErrorDto> = ex.bindingResult.fieldErrors.map { ErrorDto(code = it.code, message = it.defaultMessage) }.toSet()
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseErrorBuilder(HttpStatus.BAD_REQUEST, errors))
    }

    private fun baseErrorBuilder(httpStatus: HttpStatus, errorList: Set<ErrorDto>): ApiErrorDto {
        return ApiErrorDto(Date(), httpStatus.value(), httpStatus.name, errorList)
    }
}
