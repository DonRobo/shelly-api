package io.github.shelly.client

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.shelly.client.component.Shelly
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import kotlin.time.measureTime

class ShellyClient(
    private val host: String,
) {

    private val httpClient: HttpClient = HttpClient(Java) {
        install(ContentNegotiation) {
            jackson(streamRequestBody = false) {
                setSerializationInclusion(JsonInclude.Include.NON_NULL)
                propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
            }
        }
    }
    val objectMapper = jacksonObjectMapper().also {
        it.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }

    private data class ShellyRequest(
        val id: Int,
        val method: String,
        val params: Any?,
    )

    private data class ShellyResponse(
        val id: Int,
        val src: String,
        val result: ObjectNode?,
        val error: ObjectNode?,
    )

    private suspend fun executeCall(m: String, params: Any?): ShellyResponse {
        val response = httpClient.post("http://$host/rpc") {
            contentType(ContentType.Application.Json)
            setBody(
                ShellyRequest(
                    id = 1,
                    method = m,
                    params = params
                )
            )
        }
        return response.body()
    }

    suspend fun <Resp : Any> call(method: Method<Resp>): Resp {
        val response = executeCall(
            m = method.component + "." + method.method,
            params = null
        )

        if (response.error != null) {
            TODO("Handle ${response.error}")
        } else {
            requireNotNull(response.result)
            return objectMapper.treeToValue(response.result, method.response.java)
        }
    }
}

suspend fun main() {
    val shelly = ShellyClient("192.168.178.48")
    measureTime {
        val response = shelly.call(Shelly.GetStatus)
        response.forEach {
            println(it)
        }
    }.let {
        println("Took $it")
    }
}
