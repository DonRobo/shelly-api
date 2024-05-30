package at.robert.shelly

import at.robert.shelly.client.RawShellyClient
import at.robert.shelly.client.component.Shelly
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.treeToValue

class ShellyClient(
    ip: String,
) {

    private val client = RawShellyClient(ip)

    suspend fun getName(): String {
        TODO()
    }

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

    suspend fun getSwitches(): List<ShellySwitch> {
        val status = client.call(Shelly.GetStatus)

        return status.fieldNames().asSequence().filter {
            it.startsWith("switch:")
        }.map {
            val input = status[it]
            ShellySwitch(
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

data class ShellySwitch(
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
    val shelly = ShellyClient("192.168.178.48")
    val wifiStatus = shelly.getWifiStatus()
    println(wifiStatus)
    val inputs = shelly.getInputs()
    println(inputs)
}
