package at.robert.shelly

import at.robert.shelly.client.RawShellyClient
import at.robert.shelly.client.component.Shelly
import at.robert.shelly.client.component.Switch
import at.robert.shelly.client.component.System
import at.robert.shelly.client.component.Wifi
import at.robert.shelly.client.schema.IdParam
import at.robert.shelly.client.schema.ShellyInput
import at.robert.shelly.client.schema.ShellySwitch
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.treeToValue

class ShellyClient(
    ip: String,
) {

    private val client = RawShellyClient(ip)
    val objectMapper get() = client.objectMapper

    suspend fun getName(): String {
        return client.call(System.GetConfig)["device"]["name"].asText()
    }

    suspend fun getStatus(): ObjectNode {
        return client.call(Shelly.GetStatus)
    }

    suspend fun getWifiStatus(): ObjectNode {
        return client.call(Wifi.GetStatus)
    }

    suspend fun getWifiConfig(): ObjectNode {
        return client.call(Wifi.GetConfig)
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

    suspend fun getConfig(): ObjectNode {
        return client.call(System.GetConfig)
    }

    suspend fun getComponents(): ObjectNode {
        return client.call(Shelly.GetComponents)
    }
}


suspend fun main() {
    val shelly = ShellyClient("192.168.178.48")
    // enable pretty print
    println(shelly.getName())
    println(shelly.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(shelly.getComponents()))
}
