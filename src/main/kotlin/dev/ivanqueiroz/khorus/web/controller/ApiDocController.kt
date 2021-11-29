package dev.ivanqueiroz.khorus.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ApiDocController {
    @RequestMapping("/")
    fun index(): String = "redirect:swagger-ui/index.html"
}
