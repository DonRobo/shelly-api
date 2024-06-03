package at.robert.shelly

import at.robert.shelly.client.RawShellyClient
import at.robert.shelly.client.component.*
import at.robert.shelly.client.schema.`in`.ShellyInput
import at.robert.shelly.client.schema.`in`.ShellyInputConfig
import at.robert.shelly.client.schema.`in`.ShellyOutput
import at.robert.shelly.client.schema.`in`.ShellySwitchConfig
import at.robert.shelly.client.schema.out.ConfigPayload
import at.robert.shelly.client.schema.out.DeviceConfigPayload
import at.robert.shelly.client.schema.out.IdParam
import at.robert.shelly.client.schema.out.SetConfigPayload
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

    suspend fun setName(name: String) {
        client.call(
            System.SetConfig, SetConfigPayload(
                config = ConfigPayload(
                    device = DeviceConfigPayload(
                        name = name,
                    )
                )
            )
        )
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

    suspend fun getSwitches(): List<ShellyOutput> {
        val status = client.call(Shelly.GetStatus)

        return status.fieldNames().asSequence().filter {
            it.startsWith("switch:")
        }.map {
            val switch = status[it]
            client.objectMapper.treeToValue<ShellyOutput>(switch)
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

    suspend fun getInputConfig(inputId: Int): ShellyInputConfig {
        return client.call(Input.GetConfig, IdParam(inputId))
    }

    suspend fun getSwitchConfig(inputId: Int): ShellySwitchConfig {
        return client.call(Switch.GetConfig, IdParam(inputId))
    }
}

suspend fun main() {
    val shelly = ShellyClient("192.168.178.48")
    // enable pretty print
    println(shelly.getName())
    val inputs = shelly.getInputs()
    println(inputs)
    inputs.forEach {
        println("\t" + shelly.getInputConfig(it.id))
    }
    val outputs = shelly.getSwitches()
    println(outputs)
    outputs.forEach {
        println("\t" + shelly.getSwitchConfig(it.id))
    }
}
