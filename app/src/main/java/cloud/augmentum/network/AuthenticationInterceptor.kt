package cloud.augmentum.network

import okhttp3.Interceptor
import okhttp3.Response

@Deprecated("API uses query level authorization.")
class AuthenticationInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", apiKey)
                .build()
        )
    }
}