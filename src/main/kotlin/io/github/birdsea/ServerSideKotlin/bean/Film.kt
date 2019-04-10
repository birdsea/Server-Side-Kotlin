package io.github.birdsea.ServerSideKotlin.bean

/**
 * Filmデータクラス
 */

import java.sql.Timestamp

data class Film(
        var film_id: String = "",
        var title: String = "",
        var description: String = "",
        var release_year: String = "",
        var language_id: String = "",
        var rental_duration: String = "",
        var rental_rate: String = "",
        var length: String = "",
        var replacement_cost: String = "",
        var rating: String = "",
        var last_update: Timestamp = Timestamp(0),
        var special_features: String = "",
        var fulltext: String = ""
)