package com.fishbutcher.fskotlin

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

object ProjectTestConfig: AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringExtension)
}