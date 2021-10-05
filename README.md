# AxiosKt - Kotlin Wrapper for Axios

[![](https://jitpack.io/v/de.twiese99/axioskt.svg)](https://jitpack.io/#de.twiese99/axioskt)
![GitHub](https://img.shields.io/github/license/twiese99/axioskt)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/twiese99/axioskt)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/twiese99/axioskt)
![GitHub repo size](https://img.shields.io/github/repo-size/twiese99/axioskt)

## About
This wrapper - originally written by [@ralfstuckert](https://github.com/ralfstuckert/kotlin-react-sample/blob/master/src/axios/Axios.kt) - simplifies the use of Axios within Kotlin/JS projects.
This GitHub project serves to make the Axios wrapper available as "de.twiese99/axioskt" via Maven and Gradle for easy integration.

My experience with Axios is limited at the moment and I welcome anyone who wants to contribute to this project!
So don't be afraid to fork the project and open issues or pull requests.

## Usage

### Setup
#### Gradle
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("de.twiese99:axioskt:1.1.0")
}
```

#### Maven
Kontlin/JS projects do not run with Maven, but if anyone is interested anyway...
```xml
<project>
...
  <repositories>
    <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </repository>
  </repositories>
  <dependencies>
    <dependency>
      <groupId>de.twiese99</groupId>
      <artifactId>axioskt</artifactId>
      <version>1.1.0</version>
    </dependency>
  </dependencies>
...
</project>
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


