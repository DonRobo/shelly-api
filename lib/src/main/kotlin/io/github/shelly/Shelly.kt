package io.github.shelly

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.treeToValue
import io.github.shelly.client.ShellyClient
import io.github.shelly.client.component.Shelly

class Shelly(
    ip: String,
) {

    private val client = ShellyClient(ip)

    suspend fun getWifiStatus(): WifiStatus {
        val status = client.call(Shelly.GetStatus)
        return client.objectMapper.treeToValue(status["wifi"])
    }

    suspend fun getInputs(): List<ShellyInput> {
        val status = client.call(Shelly.GetStatus)

        return status.fieldNames().asSequence().filter {
            it.startsWith("input:")
        }.map {
            val input = status[it]
            ShellyInput(
                id = input["id"].asInt(),
                state = input["state"].asBoolean(),
            )
        }.toList()
    }
}

data class ShellyInput(
    val id: Int,
    val state: Boolean,
)

data class WifiStatus(
    @JsonProperty("sta_ip")
    val ip: String,
    val status: String,
    val ssid: String,
    val rssi: Int,
)

suspend fun main() {
    val shelly = Shelly("192.168.178.48")
    val wifiStatus = shelly.getWifiStatus()
    println(wifiStatus)
    val inputs = shelly.getInputs()
    println(inputs)
}
