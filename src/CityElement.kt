import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document
import kotlin.dom.addClass

data class CityElement(val cityName: String) : WeatherAppContract.ShowElement {
    private var cityContainer: HTMLDivElement? = null
    private var nameHolder: HTMLSpanElement? = null
    private var weatherIconHolder: HTMLImageElement? = null
    private var weatherDescriptionHolder: HTMLSpanElement? = null
    private var temperatureHolder: HTMLSpanElement? = null
    private var loadingIconHolder: HTMLImageElement? = null
    private var errorMessageHolder: HTMLSpanElement? = null

    private var locationWeather: LocationWeather? = null

    override fun isInitialized() = cityContainer != null

    override fun prepareElement(): HTMLElement {
        if (isInitialized()) {
            throw IllegalStateException("Element is initialized. \n $this")
        }
        cityContainer = document.createElement("div") as HTMLDivElement
        cityContainer?.addClass("city-container")

        nameHolder = document.createElement("span") as HTMLSpanElement
        nameHolder?.addClass("city-name")
        cityContainer?.appendChild(nameHolder!!)

        weatherIconHolder = document.createElement("img") as HTMLImageElement
        weatherIconHolder?.addClass("city-weather-icon")
        cityContainer?.appendChild(weatherIconHolder!!)

        weatherDescriptionHolder = document.createElement("span") as HTMLSpanElement
        weatherDescriptionHolder?.addClass("city-weather-desc")
        cityContainer?.appendChild(weatherDescriptionHolder!!)

        temperatureHolder = document.createElement("span") as HTMLSpanElement
        temperatureHolder?.addClass("city-temperature")
        cityContainer?.appendChild(temperatureHolder!!)

        loadingIconHolder = document.createElement("img") as HTMLImageElement
        loadingIconHolder?.addClass("city-load-icon")
        loadingIconHolder?.src = "icon/loading.svg"
        cityContainer?.appendChild(loadingIconHolder!!)

        errorMessageHolder = document.createElement("span") as HTMLSpanElement
        errorMessageHolder?.addClass("city-error-message")
        cityContainer?.appendChild(errorMessageHolder!!)

        refresh()
        return cityContainer!!
    }

    override fun refresh() {
        if (!isInitialized()) {
            throw IllegalStateException("Element is not initialized. \n$this")
        }
        fillElement(false)
        OpenWeatherMapApi.getWeatherForCity(cityName, {
            locationWeather = it
            fillElement(true)
        }, { fillElement(responseError = it) })
    }

    private fun fillElement(weatherLoaded: Boolean = false, responseError: ErrorResponse? = null) {
        val hasError = responseError != null

        if (weatherLoaded) {
            val cityWeather = locationWeather?.weather?.first()
            weatherIconHolder?.src = cityWeather?.let { OpenWeatherMapApi.getIconURL(it) } ?: ""
            weatherDescriptionHolder?.textContent = cityWeather?.description
            temperatureHolder?.textContent = "${locationWeather?.main?.temp!!}\u2103"
        }
        nameHolder?.textContent = locationWeather?.name ?: cityName
        errorMessageHolder?.textContent = if (hasError) responseError?.message else ""

        weatherIconHolder?.style?.visibility = if (weatherLoaded && !hasError) "visible" else "hidden"
        weatherDescriptionHolder?.style?.visibility = if (weatherLoaded && !hasError) "visible" else "hidden"
        temperatureHolder?.style?.visibility = if (weatherLoaded && !hasError) "visible" else "hidden"
        loadingIconHolder?.style?.visibility = if (weatherLoaded || hasError) "hidden" else "visible"
        errorMessageHolder?.style?.visibility = if (hasError) "visible" else "hidden"
    }
}