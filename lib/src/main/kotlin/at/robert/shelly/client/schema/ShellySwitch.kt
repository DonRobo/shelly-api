package at.robert.shelly.client.schema

data class ShellySwitch(
    val id: Int,
    val source: String,
    val output: Boolean,
    val temperature: ShellyTemperature,
)
