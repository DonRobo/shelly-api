package at.robert.shelly.client.component

import at.robert.shelly.client.method
import com.fasterxml.jackson.databind.node.ObjectNode

object Wifi {
    const val COMPONENT = "Wifi"

    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
    val GetConfig = method<ObjectNode>(COMPONENT, "GetConfig")
}
