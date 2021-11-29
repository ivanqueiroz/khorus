package dev.ivanqueiroz.khorus.web.dto

import java.util.*

data class ApiErrorDto(var timestamp: Date?, var status: Int?, var code: String?, var erros: Set<ErrorDto>?)
