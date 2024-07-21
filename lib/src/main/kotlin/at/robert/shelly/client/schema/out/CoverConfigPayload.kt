package at.robert.shelly.client.schema.out

import at.robert.shelly.client.schema.`in`.ShellyCoverInMode

data class CoverConfigPayload(
    val id: Int,
    val config: Config,
) {
    data class Config(
        val name: String?,
        val invertDirection: Boolean?,
        val inMode: ShellyCoverInMode?,
    )
}
