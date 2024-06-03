package at.robert.shelly.client.schema.out

import at.robert.shelly.client.schema.`in`.ShellySwitchInMode
import at.robert.shelly.client.schema.`in`.ShellySwitchInitialState

data class SwitchConfigPayload(
    val id: Int,
    val config: Config
) {
    data class Config(
        val name: String?,
        val inMode: ShellySwitchInMode?,
        val initialState: ShellySwitchInitialState?,
        val autoOn: Boolean?,
        val autoOnDelay: Int?,
        val autoOff: Boolean?,
        val autoOffDelay: Int?,
        val inputId: Int?,
    )
}
