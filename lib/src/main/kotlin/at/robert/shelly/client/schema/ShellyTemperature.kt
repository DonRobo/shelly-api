package at.robert.shelly.client.schema

import com.fasterxml.jackson.annotation.JsonProperty

data class ShellyTemperature(
    @JsonProperty("tC")
    val celsius: Double,
    @JsonProperty("tF")
    val fahrenheit: Double,
)
