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
        val base: String,
        val clouds: Clouds,
        val cod: Int,
        val coord: Coord,
        val dt: Int,
        val id: Int,
        val main: Main,
        val name: String,
        val sys: Sys,
        val visibility: Int,
        val weather: List<Weather>,
        val wind: Wind
) {
    companion object {
        fun fromJson(obj: dynamic): LocationWeather {
            return LocationWeather(obj.base.toString(),
                    Clouds.fromJson(obj.clouds),
                    obj.cod.toString().toInt(),
                    Coord.fromJson(obj.coord),
                    obj.dt.toString().toInt(),
                    obj.id.toString().toInt(),
                    Main.fromJson(obj.main),
                    obj.name.toString(),
                    Sys.fromJson(obj.sys),
                    obj.visibility.toString().toInt(),
                    List(obj.weather.length.toString().toInt()) { i -> Weather.fromJson(obj.weather[i]) },
                    Wind.fromJson(obj.wind))
        }
    }
}

data class Main(
        val humidity: Int,
        val pressure: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
) {
    companion object {
        fun fromJson(obj: dynamic): Main {
            return Main(obj.humidity.toString().toInt(),
                    obj.pressure.toString().toInt(),
                    obj.temp.toString().toDouble(),
                    obj.temp_max.toString().toDouble(),
                    obj.temp_min.toString().toDouble())
        }
    }
}

data class Wind(
        val deg: Int,
        val speed: Double
) {
    companion object {
        fun fromJson(obj: dynamic): Wind {
            return Wind(obj.deg.toString().toInt(),
                    obj.speed.toString().toDouble())
        }
    }
}

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
) {
    companion object {
        fun fromJson(obj: dynamic): Weather {
            return Weather(obj.description.toString(),
                    obj.icon.toString(),
                    obj.id.toString().toInt(),
                    obj.main.toString())
        }
    }
}

data class Sys(
        val country: String,
        val id: Int,
        val message: Double,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
) {
    companion object {
        fun fromJson(obj: dynamic): Sys {
            return Sys(obj.country.toString(),
                    obj.id.toString().toInt(),
                    obj.message.toString().toDouble(),
                    obj.sunrise.toString().toInt(),
                    obj.sunset.toString().toInt(),
                    obj.type.toString().toInt())
        }
    }
}

data class Clouds(
        val all: Int
) {
    companion object {
        fun fromJson(obj: dynamic): Clouds {
            return Clouds(obj.all.toString().toInt())
        }
    }
}

data class Coord(
        val lat: Double,
        val lon: Double
) {
    companion object {
        fun fromJson(obj: dynamic): Coord {
            return Coord(obj.lat.toString().toDouble(),
                    obj.lon.toString().toDouble())
        }
    }
}

data class ErrorResponse(
        val cod: Int,
        val message: String
) {
    companion object {
        fun fromJson(obj: dynamic): ErrorResponse {
            return ErrorResponse(obj.cod.toString().toInt(), obj.message.toString())
        }
    }
}