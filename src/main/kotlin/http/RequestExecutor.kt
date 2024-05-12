package org.example.http


enum class RequestMethod {
    GET, POST, PUT, DELETE, PATCH
}

data class Product(val name: String, val quantity: Int, val price: Double)

data class RequestEntity(val requestMethod: RequestMethod, val header: Int, val params: Double, val body: Any?)


class RequestBuilder {

    fun executeRequest(function: RequestBuilder.() -> RequestEntity): RequestEntity {
        return function()
    }

    operator fun invoke(function: RequestBuilder.() -> RequestEntity): RequestEntity {
        return function()
    }

    infix fun RequestMethod.withBody(body: Any): RequestEntity {
        return RequestEntity(this, 0, 0.0, body)
    }

    infix fun RequestEntity.withHeaders(header: Int): RequestEntity {
        return this.copy(header = header)
    }

    infix fun RequestEntity.withParams(params: Double): RequestEntity {
        return this.copy(params = params)
    }
}


fun main() {

    val requestBuilder = RequestBuilder()
    val productBody = Product("lemon", 12, 2.0)

    val requestEntity = requestBuilder.executeRequest {
        RequestMethod.POST withBody productBody withHeaders 400 withParams 55.7
    }

    println(requestEntity)
}