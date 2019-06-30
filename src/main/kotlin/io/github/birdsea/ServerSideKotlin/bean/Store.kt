package io.github.birdsea.ServerSideKotlin.bean

data class Store(
        val store_id: String,
        val country: String,
        val city: String,
        val address: String,
        val address2: String
)