package at.robert.shelly.client.schema.out

data class SystemConfigPayload(
    val config: Config,
) {
    data class Config(
        val device: Device? = null,
    ) {
        data class Device(
            val name: String? = null,
        )
    }
}
