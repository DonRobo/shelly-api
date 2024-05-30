package at.robert.shelly.client.component

import at.robert.shelly.client.method
import com.fasterxml.jackson.databind.node.ObjectNode

object Shelly {
    const val COMPONENT = "Shelly"

    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
    val ListMethods = method<ObjectNode>(COMPONENT, "ListMethods")
}

