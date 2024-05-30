package at.robert.shelly.client.component

import at.robert.shelly.client.methodWithRequestBody
import at.robert.shelly.client.schema.IdParam
import com.fasterxml.jackson.databind.node.ObjectNode

object Switch {
    const val COMPONENT = "Switch"

    val Set = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "Set")
    val Toggle = methodWithRequestBody<IdParam, ObjectNode>(COMPONENT, "Toggle")
    val SetConfig = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "SetConfig")
    val GetConfig = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "GetConfig")
    val GetStatus = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "GetStatus")
    val ResetCounters = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "ResetCounters")
}
