package at.robert.shelly.client.component

import at.robert.shelly.client.method
import at.robert.shelly.client.methodWithRequestBody
import at.robert.shelly.client.schema.`in`.ShellyInputConfig
import at.robert.shelly.client.schema.out.IdParam
import com.fasterxml.jackson.databind.node.ObjectNode

object Input {
    const val COMPONENT = "Input"

    val SetConfig = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "SetConfig")
    val GetConfig = methodWithRequestBody<IdParam, ShellyInputConfig>(COMPONENT, "GetConfig")
    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
    val CheckExpression = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "CheckExpression")
    val ResetCounters = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "ResetCounters")
}
