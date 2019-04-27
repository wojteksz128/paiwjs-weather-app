import org.w3c.xhr.XMLHttpRequest

object OpenWeatherMapApi {

    fun getWeatherForCity(cityName: String, callback: (LocationWeather) -> Unit, rollback: (ErrorResponse) -> Unit) {
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=c5489e2736d2af844806db28101147f1&lang=pl&units=metric"
        getAsync(url, { callback(LocationWeather.fromJson(JSON.parse(it))) }, { rollback(ErrorResponse.fromJson(JSON.parse(it))) })
    }

    private fun getAsync(url: String, callback: (String) -> Unit, rollback: (String) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("GET", url)
        xmlHttp.onload = {
            if (xmlHttp.readyState == 4.toShort() && xmlHttp.status == 200.toShort())
                callback(xmlHttp.responseText)
            else {
                rollback(xmlHttp.responseText)
            }
        }
        xmlHttp.send()
    }

    fun getIconURL(weather: Weather): String = "http://openweathermap.org/img/w/${weather.icon}.png"
}


data class LocationWeather(
        val base: String?,
        val clouds: Clouds,
        val cod: Int?,
        val coord: Coord?,
        val dt: Int?,
        val id: Int?,
        val main: Main,
        val name: String?,
        val sys: Sys,
        val visibility: Int?,
        val weather: List<Weather>,
        val wind: Wind
) {
    companion object {
        fun fromJson(obj: dynamic): LocationWeather {
            return LocationWeather(obj.base.toString(),
                    Clouds.fromJson(obj.clouds),
                    toOptional(obj.cod) { obj2: dynamic -> obj2.toString().toInt() },
                    Coord.fromJson(obj.coord),
                    toOptional(obj.dt) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.id) { obj2: dynamic -> obj2.toString().toInt() },
                    Main.fromJson(obj.main),
                    toOptional(obj.name) { obj2: dynamic -> obj2.toString() },
                    Sys.fromJson(obj.sys),
                    toOptional(obj.visibility) { obj2: dynamic -> obj2.toString().toInt() },
                    List(obj.weather.length.toString().toInt()) { i -> Weather.fromJson(obj.weather[i]) },
                    Wind.fromJson(obj.wind))
        }
    }
}

data class Main(
        val humidity: Int?,
        val pressure: Int?,
        val temp: Double?,
        val temp_max: Double?,
        val temp_min: Double?
) {
    companion object {
        fun fromJson(obj: dynamic): Main {
            return Main(toOptional(obj.humidity) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.pressure) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.temp) { obj2: dynamic -> obj2.toString().toDouble() },
                    toOptional(obj.temp_max) { obj2: dynamic -> obj2.toString().toDouble() },
                    toOptional(obj.temp_min) { obj2: dynamic -> obj2.toString().toDouble() })
        }
    }
}

data class Wind(
        val deg: Int?,
        val speed: Double?
) {
    companion object {
        fun fromJson(obj: dynamic): Wind {
            return Wind(toOptional(obj.deg) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.speed) { obj2: dynamic -> obj2.toString().toDouble() })
        }
    }
}

data class Weather(
        val description: String?,
        val icon: String?,
        val id: Int?,
        val main: String?
) {
    companion object {
        fun fromJson(obj: dynamic): Weather {
            return Weather(toOptional(obj.description) { obj2: dynamic -> obj2.toString() },
                    toOptional(obj.icon) { obj2: dynamic -> obj2.toString() },
                    toOptional(obj.id) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.main) { obj2: dynamic -> obj2.toString() })
        }
    }
}

data class Sys(
        val country: String?,
        val id: Int?,
        val message: Double?,
        val sunrise: Int?,
        val sunset: Int?,
        val type: Int?
) {
    companion object {
        fun fromJson(obj: dynamic): Sys {
            return Sys(toOptional(obj.country) { obj2: dynamic -> obj2.toString() },
                    toOptional(obj.id) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.message) { obj2: dynamic -> obj2.toString().toDouble() },
                    toOptional(obj.sunrise) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.sunset) { obj2: dynamic -> obj2.toString().toInt() },
                    toOptional(obj.type) { obj2: dynamic -> obj2.toString().toInt() })
        }
    }
}

data class Clouds(
        val all: Int?
) {
    companion object {
        fun fromJson(obj: dynamic): Clouds {
            return Clouds(toOptional(obj.all) { obj2: dynamic -> obj2.toString().toInt() })
        }
    }
}

data class Coord(
        val lat: Double?,
        val lon: Double?
) {
    companion object {
        fun fromJson(obj: dynamic): Coord {
            return Coord(toOptional(obj.lat) { obj2: dynamic -> obj2.toString().toDouble() },
                    toOptional(obj.lon) { obj2: dynamic -> obj2.toString().toDouble() })
        }
    }
}

data class ErrorResponse(
        val cod: Int?,
        val message: String?
) {
    companion object {
        fun fromJson(obj: dynamic): ErrorResponse {
            return ErrorResponse(toOptional(obj.cod) { obj2: dynamic -> obj2.toString().toInt() }, toOptional(obj.message) { obj2: dynamic -> obj2.toString() })
        }
    }
}

fun <T> toOptional(obj: dynamic, callback: (dynamic) -> T?) = if (obj != null) callback(obj) else null