package at.robert.shelly.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef

interface Method<Resp : Any> {
    val component: String
    val method: String
    val response: TypeReference<Resp>
}

interface MethodWithRequestBody<Req : Any, Resp : Any> {
    val component: String
    val method: String
    val request: TypeReference<Req>
    val response: TypeReference<Resp>
}

data class IMethod<Resp : Any>(
    override val component: String,
    override val method: String,
    override val response: TypeReference<Resp>,
) : Method<Resp>

data class IMethodWithRequestBody<Req : Any, Resp : Any>(
    override val component: String,
    override val method: String,
    override val request: TypeReference<Req>,
    override val response: TypeReference<Resp>,
) : MethodWithRequestBody<Req, Resp>

inline fun <reified T : Any> method(component: String, method: String): Method<T> =
    IMethod(component, method, jacksonTypeRef<T>())
inline fun <reified Req : Any, reified Resp : Any> methodWithRequestBody(
    component: String,
    method: String
): MethodWithRequestBody<Req, Resp> =
    IMethodWithRequestBody(component, method, jacksonTypeRef<Req>(), jacksonTypeRef<Resp>())
