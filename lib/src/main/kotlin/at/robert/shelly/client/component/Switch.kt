package at.robert.shelly.client.component

import at.robert.shelly.client.methodWithRequestBody
import at.robert.shelly.client.schema.`in`.ShellySwitchConfig
import at.robert.shelly.client.schema.out.IdParam
import at.robert.shelly.client.schema.out.SwitchConfigPayload
import com.fasterxml.jackson.databind.node.ObjectNode

object Switch {
    const val COMPONENT = "Switch"

    val Set = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "Set")
    val Toggle = methodWithRequestBody<IdParam, ObjectNode>(COMPONENT, "Toggle")
    val SetConfig = methodWithRequestBody<SwitchConfigPayload, ObjectNode>(COMPONENT, "SetConfig")
    val GetConfig = methodWithRequestBody<IdParam, ShellySwitchConfig>(COMPONENT, "GetConfig")
    val GetStatus = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "GetStatus")
    val ResetCounters = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "ResetCounters")
}
