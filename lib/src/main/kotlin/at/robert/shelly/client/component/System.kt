package at.robert.shelly.client.component

import at.robert.shelly.client.method
import at.robert.shelly.client.methodWithRequestBody
import at.robert.shelly.client.schema.out.SetConfigPayload
import com.fasterxml.jackson.databind.node.ObjectNode

object System {
    const val COMPONENT = "Sys"

    val SetConfig = methodWithRequestBody<SetConfigPayload, ObjectNode>(COMPONENT, "SetConfig")
    val GetConfig = method<ObjectNode>(COMPONENT, "GetConfig")
    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
}
