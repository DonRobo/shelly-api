package at.robert.shelly.client.component

import at.robert.shelly.client.methodWithRequestBody
import at.robert.shelly.client.schema.`in`.ShellyCoverConfig
import at.robert.shelly.client.schema.out.CoverConfigPayload
import at.robert.shelly.client.schema.out.IdParam
import com.fasterxml.jackson.databind.node.ObjectNode

object Cover {
    const val COMPONENT = "Cover"

    val GetConfig = methodWithRequestBody<IdParam, ShellyCoverConfig>(COMPONENT, "GetConfig")
    val SetConfig = methodWithRequestBody<CoverConfigPayload, ObjectNode>(Input.COMPONENT, "SetConfig")

}
