package dev.ivanqueiroz.khorus.config

import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.core.task.TaskExecutor
import org.springframework.http.HttpMethod
import org.springframework.web.reactive.function.client.WebClient
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.builders.ResponseBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class KhorusConfig {

    @Bean
    fun webClient() : WebClient {
        return WebClient.create()
    }

    @Bean
    fun taskExecutor(): TaskExecutor {
        return SimpleAsyncTaskExecutor()
    }

    @Bean
    fun cacheManager(): CacheManager {
        val concurrentMapCacheManager = ConcurrentMapCacheManager()
        concurrentMapCacheManager.isAllowNullValues = false
        return concurrentMapCacheManager
    }

    @Bean
    fun api(): Docket? {
        val apiInfo = ApiInfo(
            "Horus Currency Converter REST API", "The API realizes conversion between currencies of 4 types: (BRL, USD, EUR, JPY).",
            "1.0.0", "", Contact("Ivan Queiroz", "", "ivanqueiroz@gmail.com"), "", "", emptyList()
        )
        val globalResponses = listOf(
            ResponseBuilder()
                .code("200")
                .description("OK")
                .build(),
            ResponseBuilder()
                .code("400")
                .description("Bad Request")
                .build(),
            ResponseBuilder()
                .code("500")
                .description("Internal Error")
                .build()
        )
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .globalResponses(HttpMethod.GET, globalResponses)
            .globalResponses(HttpMethod.POST, globalResponses)
            .globalResponses(HttpMethod.DELETE, globalResponses)
            .globalResponses(HttpMethod.PATCH, globalResponses)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.ant("/currency/**"))
            .build()
            .apiInfo(apiInfo)
    }

}
