package io.github.birdsea.ServerSideKotlin.component

/**
 * DbManagerクラス
 */

import io.github.birdsea.ServerSideKotlin.bean.Film
import jooq.tables.FilmTable.FILM
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "db")
object DbManager {
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String

    /**
     * トランザクション開始
     */
    fun startTransaction() {
    }

    /**
     * トランザクション終了
     */
    fun endTransaction() {
    }

    fun selectFilm(): MutableList<Film> {

        val result = mutableListOf<Film>()

        val settings = Settings()
        settings.setExecuteLogging(true)  // jOOQのログ出力を行うか
        settings.withRenderFormatted(true) // SQL文の出力を見易い形にフォーマットするか
        settings.withRenderSchema(false) // SQL文にスキーマを出力するか

        DSL.using(
                url,
                username,
                password
        ).use { ctx ->

            //SELECT文の実行
            val film = FILM
            ctx.select().from(film).orderBy(film.FILM_ID)
                    .forEach {
                        result.add(Film(
                                it[film.FILM_ID].toString(),
                                it[film.TITLE],
                                it[film.DESCRIPTION],
                                it[film.RELEASE_YEAR].toString(),
                                it[film.LANGUAGE_ID].toString(),
                                it[film.RENTAL_DURATION].toString(),
                                it[film.RENTAL_RATE].toString(),
                                it[film.LENGTH].toString(),
                                it[film.REPLACEMENT_COST].toString(),
                                it[film.RATING].toString(),
                                it[film.LAST_UPDATE],
                                it[film.SPECIAL_FEATURES].toString(),
                                it[film.FULLTEXT].toString()))
                    }
            ctx.close()
        }
        return result
    }
}
