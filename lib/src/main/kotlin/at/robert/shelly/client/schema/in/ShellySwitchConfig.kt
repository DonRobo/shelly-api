package at.robert.shelly.client.schema.`in`

import com.fasterxml.jackson.annotation.JsonProperty

data class ShellySwitchConfig(
    val id: Int,
    val name: String?,
    val inMode: ShellySwitchInMode,
    val initialState: ShellySwitchInitialState,
    val autoOn: Boolean,
    val autoOnDelay: Int,
    val autoOff: Boolean,
    val autoOffDelay: Int,
    val inputId: Int,
)

enum class ShellySwitchInMode {
    @JsonProperty("momentary")
    MOMENTARY,

    @JsonProperty("follow")
    FOLLOW,

    @JsonProperty("flip")
    FLIP,

    @JsonProperty("detached")
    DETACHED,

    @JsonProperty("cycle")
    CYCLE,

    @JsonProperty("activate")
    ACTIVATE
}

enum class ShellySwitchInitialState {
    @JsonProperty("off")
    OFF,

    @JsonProperty("on")
    ON,

    @JsonProperty("restore_last")
    RESTORE_LAST,

    @JsonProperty("match_input")
    MATCH_INPUT
}
