package at.robert.shelly.client.schema.`in`

import com.fasterxml.jackson.annotation.JsonProperty

data class ShellyInputConfig(
    val id: Int,
    val name: String?,
    val type: ShellyInputType,
    val enable: Boolean,
    val invert: Boolean?,
    val factoryReset: Boolean?,
)

enum class ShellyInputType {
    @JsonProperty("switch")
    SWITCH,

    @JsonProperty("button")
    BUTTON,

    @JsonProperty("analog")
    ANALOG,

    @JsonProperty("count")
    COUNT
}
