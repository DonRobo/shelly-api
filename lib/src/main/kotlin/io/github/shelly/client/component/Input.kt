package io.github.shelly.client.component

import com.fasterxml.jackson.databind.node.ObjectNode
import io.github.shelly.client.method
import io.github.shelly.client.methodWithRequestBody

object Input {
    const val COMPONENT = "Input"

    val SetConfig = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "SetConfig")
    val GetConfig = method<ObjectNode>(COMPONENT, "GetConfig")
    val GetStatus = method<ObjectNode>(COMPONENT, "GetStatus")
    val CheckExpression = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "CheckExpression")
    val ResetCounters = methodWithRequestBody<ObjectNode, ObjectNode>(COMPONENT, "ResetCounters")
}
