package io.github.birdsea.ServerSideKotlin.controller

/**
 * FilmContrllerクラス
 */
import io.github.birdsea.ServerSideKotlin.component.DbManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class FilmController {
    @GetMapping("")
    fun index(model: Model): String {

        // DBからFilm情報を取得
        var films = DbManager.selectFilm()
        model.addAttribute("films", films)

        return "film"
    }
}