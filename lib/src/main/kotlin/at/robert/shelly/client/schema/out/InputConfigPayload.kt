package at.robert.shelly.client.schema.out

import at.robert.shelly.client.schema.`in`.ShellyInputType

data class InputConfigPayload(
    val id: Int,
    val config: Config
) {
    data class Config(
        val name: String?,
        val type: ShellyInputType?,
        val enable: Boolean?,
        val invert: Boolean?,
        val factoryReset: Boolean?,
    )
}
