package io.github.birdsea.ServerSideKotlin.component

/**
 * DbManagerクラス
 */

import io.github.birdsea.ServerSideKotlin.bean.Film
import io.github.birdsea.ServerSideKotlin.bean.Store
import jooq.tables.AddressTable.ADDRESS
import jooq.tables.CityTable.CITY
import jooq.tables.CountryTable.COUNTRY
import jooq.tables.FilmTable.FILM
import jooq.tables.StoreTable.STORE
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

    fun selectStore(): MutableList<Store> {

        val result = mutableListOf<Store>()

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
            ctx.select(STORE.STORE_ID, COUNTRY.COUNTRY_, CITY.CITY_, ADDRESS.ADDRESS_, ADDRESS.ADDRESS2)
                    .from(STORE)
                    .join(ADDRESS).on(ADDRESS.ADDRESS_ID.eq(STORE.address().ADDRESS_ID))
                    .join(CITY).on(CITY.CITY_ID.eq(ADDRESS.city().CITY_ID))
                    .join(COUNTRY).on(COUNTRY.COUNTRY_ID.eq(CITY.country().COUNTRY_ID))
                    .forEach {
                        result.add(Store(
                                it[STORE.STORE_ID].toString(),
                                it[COUNTRY.COUNTRY_],
                                it[CITY.CITY_],
                                it[ADDRESS.ADDRESS_],
                                it[ADDRESS.ADDRESS2] ?: "")
                        )
                    }
            ctx.close()
        }
        return result
    }
}
