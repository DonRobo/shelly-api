package at.robert.shelly.client

import kotlin.reflect.KClass

interface Method<Resp : Any> {
    val component: String
    val method: String
    val response: KClass<Resp>
}

interface MethodWithRequestBody<Req : Any, Resp : Any> {
    val component: String
    val method: String
    val request: KClass<Req>
    val response: KClass<Resp>
}

data class IMethod<Resp : Any>(
    override val component: String,
    override val method: String,
    override val response: KClass<Resp>,
) : Method<Resp>

data class IMethodWithRequestBody<Req : Any, Resp : Any>(
    override val component: String,
    override val method: String,
    override val request: KClass<Req>,
    override val response: KClass<Resp>,
) : MethodWithRequestBody<Req, Resp>

inline fun <reified T : Any> method(component: String, method: String): Method<T> = IMethod(component, method, T::class)
inline fun <reified Req : Any, reified Resp : Any> methodWithRequestBody(
    component: String,
    method: String
): MethodWithRequestBody<Req, Resp> =
    IMethodWithRequestBody(component, method, Req::class, Resp::class)
