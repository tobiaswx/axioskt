# AxiosKt
Kotlin Wrapper for Axios

## About
This wrapper - originally written by [@ralfstuckert](https://github.com/ralfstuckert/kotlin-react-sample/blob/master/src/axios/Axios.kt) - simplifies the use of Axios within Kotlin/JS projects.
This GitHub project serves to make the Axios wrapper available as "AxiosKt" via Maven and Gradle for easy integration.

My experience with Axios is limited at the moment and I welcome anyone who wants to contribute to this project!
So don't be afraid to fork the project and open issues or pull requests.

## Usage

### Setup
**<ins>All artifacts created by the project can be viewed here:</ins>** [AxiosKt Packages](https://github.com/twiese99?tab=packages&repo_name=AxiosKt)
#### Gradle
```kotlin
dependencies {
    implementation("de.twiese99:axioskt:1.0.0")
}
```

#### Maven
Kontlin/JS projects do not run with Maven, but if anyone is interested anyway...
```xml
<dependency>
  <groupId>de.twiese99</groupId>
  <artifactId>axioskt</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Example Code

```kotlin
fun main() {
  GlobalScope.launch {
        val data = Axios.get<dynamic>(myUrl).await().data
        doSomethingWith(data)
    }
}
```

### Recommendation
I personally prefer to use axios together with kotlinx-serialization - especially in multiplatform projects where data classes are defined in the common.
For example, if you want to query a json api and have the result automatically converted to a kotlin object, you can write functions like this one:

```kotlin
import kotlinx.coroutines.await
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromDynamic
import kotlinx.serialization.json.Json

public suspend inline fun axiosGet(url: String, config: AxiosRequestConfig? = null) : dynamic {
    return if(config != null) {
        Axios.get<dynamic>(url, config).await().data
    } else {
        Axios.get<dynamic>(url).await().data
    }
}

@ExperimentalSerializationApi
public suspend inline fun <reified T> axiosGet(url: String, config: AxiosRequestConfig? = null) : T {
    return Json { isLenient = true }.decodeFromDynamic(axiosGet(url, config))
}
```

After that, you could query data from the api as follows:
```kotlin
GlobalScope.launch {
    axiosGet<Customer>("http://localhost:8080/api/customer/1").let {
        console.log("Name: ${it.name}, Age: ${it.age}")
    }
}
```

**Please note: In order for (de)serialization with kotlinx-serialization to run smoothly, (data) classes must be annotated with @Serializable.**

If necessary you have to write your own converters for single classes or parameters.
More about this can be found here: https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serializers.md


