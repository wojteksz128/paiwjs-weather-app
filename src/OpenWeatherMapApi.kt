import org.w3c.xhr.XMLHttpRequest

object OpenWeatherMapApi {

    fun getWeatherForCity(cityName: String, callback: (LocationWeather) -> Unit) {
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=c5489e2736d2af844806db28101147f1"
        getAsync(url) { callback(JSON.parse(it)) }
    }

    private fun getAsync(url: String, callback: (String) -> Unit) {
        val xmlHttp = XMLHttpRequest()
        xmlHttp.open("GET", url)
        xmlHttp.onload = {
            if (xmlHttp.readyState == 4.toShort() && xmlHttp.status == 200.toShort())
                callback(xmlHttp.responseText)
        }
        xmlHttp.send()
    }
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
)

data class Main(
        val humidity: Int,
        val pressure: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
)

data class Wind(
        val deg: Int,
        val speed: Double
)

data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
)

data class Sys(
        val country: String,
        val id: Int,
        val message: Double,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
)

data class Clouds(
        val all: Int
)

data class Coord(
        val lat: Double,
        val lon: Double
)