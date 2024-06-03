package at.robert.shelly.client.schema.`in`

import com.fasterxml.jackson.annotation.JsonProperty

data class WifiStatus(
    @JsonProperty("sta_ip")
    val ip: String,
    val status: String,
    val ssid: String,
    val rssi: Int,
)
