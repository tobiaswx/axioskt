package axios

import kotlinx.coroutines.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic

val json: Json by lazy {
    Json { isLenient = true }
}

/**
 * Executes a get request to the given URL and returns the data object as dynamic.
 * The function does not perform any exception handling.
 */
public suspend inline fun axiosGet(url: String, config: AxiosRequestConfig? = null) : dynamic {
    return if(config != null) {
        Axios.get<dynamic>(url, config).await().data
    } else {
        Axios.get<dynamic>(url).await().data
    }
}

/**
 * Executes a get request to the given URL and returns the data object as decoded object. Uses [Json.decodeFromDynamic].
 * The function does not perform any exception handling.
 */
@ExperimentalSerializationApi
public suspend inline fun <reified T> axiosGet(url: String, config: AxiosRequestConfig? = null) : T {
    return json.decodeFromDynamic(axiosGet(url, config))
}
