package at.robert.shelly.client.schema.`in`

import com.fasterxml.jackson.annotation.JsonProperty

data class ShellyCover(
    val id: Int,
    val source: String,
    val state: String,
    val apower: Double,
    val voltage: Double,
    val pf: Double,
    val freq: Double,
    @JsonProperty("aenergy")
    val energy: ShellyEnergy,
    val temperature: ShellyTemperature,
    val posControl: Boolean,
    val lastDirection: String,
)
