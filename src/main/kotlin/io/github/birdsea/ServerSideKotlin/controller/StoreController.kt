package io.github.birdsea.ServerSideKotlin.controller

/**
 * StoreContrllerクラス
 */
import io.github.birdsea.ServerSideKotlin.component.DbManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
open class StoreController {
    @GetMapping("store")
    fun store(model: Model): String {

        // DBからStore情報を取得
        var stores = DbManager.selectStore()
        model.addAttribute("stores", stores)

        return "store"
    }
}