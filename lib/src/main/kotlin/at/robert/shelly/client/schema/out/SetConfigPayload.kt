package at.robert.shelly.client.schema.out

data class SetConfigPayload(
    val config: ConfigPayload,
)

data class ConfigPayload(
    val device: DeviceConfigPayload? = null,
)

data class DeviceConfigPayload(
    val name: String? = null,
)
