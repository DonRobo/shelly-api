package at.robert.shelly

import at.robert.shelly.client.RawShellyClient
import at.robert.shelly.client.component.*
import at.robert.shelly.client.schema.`in`.*
import at.robert.shelly.client.schema.out.*
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
            System.SetConfig, SystemConfigPayload(
                config = SystemConfigPayload.Config(
                    device = SystemConfigPayload.Config.Device(
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

    suspend fun getCovers(): List<ShellyCover> {
        val status = client.call(Shelly.GetStatus)

        return status.fieldNames().asSequence().filter {
            it.startsWith("cover:")
        }.map {
            val switch = status[it]
            client.objectMapper.treeToValue<ShellyCover>(switch)
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

    suspend fun setConfig(consumptionTypes: List<String>?) {
        client.call(
            System.SetConfig, SystemConfigPayload(
                config = SystemConfigPayload.Config(
                    uiData = SystemConfigPayload.Config.UiData(
                        consumptionTypes = consumptionTypes,
                    )
                )
            )
        )
    }

    suspend fun getComponents(): ObjectNode {
        return client.call(Shelly.GetComponents)
    }

    suspend fun getInputConfig(inputId: Int): ShellyInputConfig {
        return client.call(Input.GetConfig, IdParam(inputId))
    }

    suspend fun getSwitchConfig(switchId: Int): ShellySwitchConfig {
        return client.call(Switch.GetConfig, IdParam(switchId))
    }

    suspend fun getCoverConfig(switchId: Int): ShellyCoverConfig {
        return client.call(Cover.GetConfig, IdParam(switchId))
    }

    suspend fun setInputConfig(
        inputId: Int,
        name: String? = null,
        enable: Boolean? = null,
        type: ShellyInputType? = null,
        invert: Boolean? = null,
        factoryReset: Boolean? = null,
    ) {
        client.call(
            Input.SetConfig, InputConfigPayload(
                id = inputId,
                config = InputConfigPayload.Config(
                    name = name,
                    enable = enable,
                    type = type,
                    invert = invert,
                    factoryReset = factoryReset,
                )
            )
        )
    }

    suspend fun setSwitchConfig(
        switchId: Int,
        name: String? = null,
        inMode: ShellySwitchInMode? = null,
        initialState: ShellySwitchInitialState? = null,
        autoOn: Boolean? = null,
        autoOnDelay: Int? = null,
        autoOff: Boolean? = null,
        autoOffDelay: Int? = null,
        inputId: Int? = null,
    ) {
        client.call(
            Switch.SetConfig, SwitchConfigPayload(
                id = switchId,
                config = SwitchConfigPayload.Config(
                    name = name,
                    inMode = inMode,
                    initialState = initialState,
                    autoOn = autoOn,
                    autoOnDelay = autoOnDelay,
                    autoOff = autoOff,
                    autoOffDelay = autoOffDelay,
                    inputId = inputId,
                )
            )
        )
    }

    suspend fun setCoverConfig(
        coverId: Int,
        name: String? = null,
        invertDirections: Boolean? = null,
        inMode: ShellyCoverInMode? = null,
    ) {
        client.call(
            Cover.SetConfig, CoverConfigPayload(
                id = coverId,
                config = CoverConfigPayload.Config(
                    name = name,
                    invertDirection = invertDirections,
                    inMode = inMode,
                )
            )
        )
    }
}

suspend fun main() {
    val shelly = ShellyClient("192.168.1.120")
    println(shelly.getName())
    println(shelly.getConfig())
}
