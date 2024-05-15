package io.github.shelly.client.component

import com.fasterxml.jackson.databind.node.ObjectNode
import io.github.shelly.client.method

object Shelly {
    const val COMPONENT = "Shelly"

    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
}

