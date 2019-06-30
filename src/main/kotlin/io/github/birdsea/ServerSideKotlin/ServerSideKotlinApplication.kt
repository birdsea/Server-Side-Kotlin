package io.github.birdsea.ServerSideKotlin

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
open class ServerSideKotlinApplication

fun main(args: Array<String>) {
	runApplication<ServerSideKotlinApplication>(*args)
}
