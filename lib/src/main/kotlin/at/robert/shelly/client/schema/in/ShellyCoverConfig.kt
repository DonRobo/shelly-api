package at.robert.shelly.client.schema.`in`

import com.fasterxml.jackson.annotation.JsonProperty

data class ShellyCoverConfig(
    val id: Int,
    val name: String,
    val motor: ShellyCoverMotor,
    val maxtimeOpen: Double,
    val maxtimeClose: Double,
    val initialState: ShellyCoverInitialState,
    val invertDirections: Boolean,
    val inMode: ShellyCoverInMode,
    val swapInputs: Boolean,
    val safetySwitch: ShellyCoverSafetySwitch,
    val powerLimit: Int,
    val voltageLimit: Int,
    val undervoltageLimit: Int,
    val currentLimit: Double,
    val obstructionDetection: ShellyCoverObstructionDetection,
)

data class ShellyCoverMotor(
    val idlePowerThr: Double,
    val idleConfirmPeriod: Double,
)

data class ShellyCoverSafetySwitch(
    val enable: Boolean,
    val direction: ShellyCoverDirection,
    val action: ShellyCoverSafetySwitchAction,
    val allowedMove: ShellyCoverSafetySwitchAllowedMove?,
)

data class ShellyCoverObstructionDetection(
    val enable: Boolean,
    val direction: ShellyCoverDirection,
    val action: ShellyCoverObstructionDetectionAction,
    val powerThr: Int,
    val holdoff: Double,
)

enum class ShellyCoverInitialState {
    @JsonProperty("open")
    OPEN,

    @JsonProperty("close")
    CLOSE,

    @JsonProperty("stopped")
    STOPPED,
}

enum class ShellyCoverInMode {
    @JsonProperty("single")
    SINGLE,

    @JsonProperty("dual")
    DUAL,

    @JsonProperty("detached")
    DETACHED,
}

enum class ShellyCoverSafetySwitchAction {
    @JsonProperty("stop")
    STOP,

    @JsonProperty("reverse")
    REVERSE,

    @JsonProperty("pause")
    PAUSE,
}

enum class ShellyCoverSafetySwitchAllowedMove {
    @JsonProperty("reverse")
    REVERSE,
}

enum class ShellyCoverDirection {
    @JsonProperty("open")
    OPEN,

    @JsonProperty("close")
    CLOSE,

    @JsonProperty("both")
    BOTH,
}

enum class ShellyCoverObstructionDetectionAction {
    @JsonProperty("stop")
    STOP,

    @JsonProperty("reverse")
    REVERSE,
}
