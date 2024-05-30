package at.robert.shelly

import at.robert.shelly.client.RawShellyClient
import at.robert.shelly.client.component.Shelly
import at.robert.shelly.client.component.Switch
import at.robert.shelly.client.schema.IdParam
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.node.ObjectNode
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
            val switch = status[it]
            client.objectMapper.treeToValue<ShellySwitch>(switch)
        }.toList()
    }

    suspend fun getMethods(): List<String> {
        return client.call(Shelly.ListMethods)["methods"].map {
            it.asText()
        }
    }

    suspend fun toggleSwitch(switchIndex: Int): ObjectNode {
        return client.call(Switch.Toggle, IdParam(switchIndex))
    }
}

data class ShellyInput(
    val id: Int,
    val state: Boolean,
)

data class ShellyTemperature(
    @JsonProperty("tC")
    val celsius: Double,
    @JsonProperty("tF")
    val fahrenheit: Double,
)

data class ShellySwitch(
    val id: Int,
    val source: String,
    val output: Boolean,
    val temperature: ShellyTemperature,
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
    val switches = shelly.getSwitches()
    println(switches)
    println(shelly.toggleSwitch(0))
    val methods: List<String> = shelly.getMethods()
    methods.sorted().forEach {
        println(it)
    }
}
