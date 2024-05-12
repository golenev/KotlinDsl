package http

import http.RequestMethod.*

enum class RequestMethod {
    GET, POST, PUT, DELETE, PATCH;
}

data class Product(val name: String, val quantity: Int, val price: Double)

data class RequestEntity<T, K, V>(
    val requestMethod: RequestMethod,
    val headers: Map<K, V>,
    val params: Map<K, V>,
    val body: T?
)

class RequestExecutor {

    operator fun <T> invoke(function: RequestExecutor.() -> T): T {
        return function()
    }

    infix fun RequestMethod.withBody(body: Any): RequestEntity<Any, Any, Any> {
        return RequestEntity(this, emptyMap(), emptyMap(), body)
    }

    infix fun <T, K, V> T.withHeaders(headers: Map<K, V>): RequestEntity<T, K, V> {
        return when (this) {
            is RequestEntity<*, *, *> -> (this as RequestEntity<T, K, V>).copy(headers = headers)
            is RequestMethod -> RequestEntity(this, headers, emptyMap(), null)
            else -> throw IllegalArgumentException("Invalid type for withHeaders")
        }
    }

    infix fun <T, K, V> T.withParams(params: Map<K, V>): RequestEntity<T, K, V> {
        return when (this) {
            is RequestEntity<*, *, *> -> (this as RequestEntity<T, K, V>).copy(params = params)
            is RequestMethod -> RequestEntity(this, emptyMap(), params, null)
            else -> throw IllegalArgumentException("Invalid type for withParams")
        }
    }

}

fun main() {
    val requestExecutor = RequestExecutor()
    val productBody = Product("lemon", 12, 2.0)
    val headers = mapOf("Content-Length" to 400, "isOrigin" to true)
    val params = mapOf("isActive" to true, "isAvailable" to false)

    val postRequestEntity = requestExecutor {
        POST withBody productBody withHeaders headers withParams params
    }

    val getRequestEntity = requestExecutor {
        GET withHeaders headers withParams params
    }

    println(getRequestEntity)
    println(postRequestEntity)
}