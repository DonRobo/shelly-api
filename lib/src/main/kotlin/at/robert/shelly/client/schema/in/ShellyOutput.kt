package at.robert.shelly.client.schema.`in`

data class ShellyOutput(
    val id: Int,
    val source: String,
    val output: Boolean,
    val temperature: ShellyTemperature,
)
