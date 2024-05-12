package http

import http.RequestMethod.*

enum class RequestMethod {
    GET, POST, PUT, DELETE, PATCH;
}

data class Product(val name: String, val quantity: Int, val price: Double)

data class RequestEntity<T, K, V>(
    val requestMethod: RequestMethod,
    val headers: Map<K, V>,
    val params: Double,
    val body: T?
)

class RequestExecutor {
    operator fun <T> invoke(function: RequestExecutor.() -> T): T {
        return function()
    }

    infix fun <T> T.withBody(body: Any): RequestEntity<Any, *, *> {
        return when (this) {
            is RequestEntity<*, *, *> -> (this as RequestEntity<Any, *, *>).copy(body = body)
            is RequestMethod -> RequestEntity(this, emptyMap<Any, Any>(), 0.0, body)
            else -> throw IllegalArgumentException("Invalid type for withBody")
        }
    }

    infix fun <T, K, V> T.withHeaders(headers: Map<K, V>): RequestEntity<T, K, V> {
        return when (this) {
            is RequestEntity<*, *, *> -> (this as RequestEntity<T, K, V>).copy(headers = headers)
            is RequestMethod -> RequestEntity(this, headers, 0.0, null)
            else -> throw IllegalArgumentException("Invalid type for withHeaders")
        }
    }

    infix fun <T> T.withParams(params: Double): RequestEntity<T, *, *> {
        return when (this) {
            is RequestEntity<*, *, *> -> (this as RequestEntity<T, Any, Any>).copy(params = params)
            is RequestMethod -> RequestEntity(this, emptyMap<Any, Any>(), params, null)
            else -> throw IllegalArgumentException("Invalid type for withParams")
        }
    }
}

fun main() {
    val requestExecutor = RequestExecutor()
    val productBody = Product("lemon", 12, 2.0)
    val headers = mapOf("Content-Length" to 400, "isOrigin" to true)

    val postRequestEntity = requestExecutor {
        POST withBody productBody withHeaders headers withParams 55.7
    }

    val getRequestEntity = requestExecutor {
        GET withHeaders headers withParams 55.7
    }

    println(getRequestEntity)
    println(postRequestEntity)
}