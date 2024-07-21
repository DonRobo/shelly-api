package at.robert.shelly.client.schema.`in`

data class ShellyEnergy(
    val total: Double,
    val byMinute: List<Double>,
    val minuteTs: Int,
)
