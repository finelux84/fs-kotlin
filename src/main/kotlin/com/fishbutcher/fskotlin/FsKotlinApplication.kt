package com.fishbutcher.fskotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FsKotlinApplication

fun main(args: Array<String>) {
    runApplication<FsKotlinApplication>(*args)
}
