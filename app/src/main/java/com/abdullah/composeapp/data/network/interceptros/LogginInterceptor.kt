package com.abdullah.composeapp.data.network.interceptros

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

class CURLInterceptor(private var enabled: Boolean) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!enabled) {
            return chain.proceed(request)
        }
        val requestBody = request.body
        val headers = request.headers
        val method = request.method
        val path = request.url

        val baseString =
            StringBuilder("curl -X $method '$path'")

        for (i in 0 until headers.size) {
            val name = headers.name(i)
            val value = headers.value(i)
            baseString.append(" -H '").append(name).append(": ").append(value).append("'")
        }
        if (requestBody != null) {
            val buffer = Buffer()
            var charset = Charset.forName("UTF-8")
            requestBody.writeTo(buffer)
            val contentType: MediaType? = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset()
            }
            if (charset == null) {
                baseString.append(" --data '").append(buffer.readUtf8()).append("'")
            } else {
                baseString.append(" --data '").append(buffer.readString(charset)).append("'")
            }
        }
        Timber.d(baseString.toString())
        return chain.proceed(request)
    }

}
