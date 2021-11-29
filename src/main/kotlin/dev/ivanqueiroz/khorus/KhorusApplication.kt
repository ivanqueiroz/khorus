package dev.ivanqueiroz.khorus

import dev.ivanqueiroz.khorus.util.getLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

private const val HOUR = 1000 * 60 * 60

@ConfigurationPropertiesScan("dev.ivanqueiroz.khorus.exchangeapi")
@SpringBootApplication
@EnableScheduling
@EnableCaching
class KhorusApplication(val cacheManager: CacheManager) {

    companion object {
        private val log = getLogger(KhorusApplication::class.java)
    }

    @Scheduled(fixedRate = 2L * HOUR)
    fun evictAllCaches() {
        cacheManager.cacheNames.forEach { cacheName -> cacheManager.getCache(cacheName)?.clear() }
    }
}

fun main(args: Array<String>) {
    val logger = getLogger(KhorusApplication::class.java)
    logger.info("Khorus starting")
    runApplication<KhorusApplication>(*args)
    logger.info("Khorus started")
}
