import org.w3c.dom.*
import kotlin.browser.document
import kotlin.dom.addClass

data class CityElement(val cityName: String) : WeatherAppContract.ShowElement {
    private var cityContainer: HTMLDivElement? = null
    private var nameHolder: HTMLHeadingElement? = null
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
        cityContainer?.addClass("col-12", "col-sm-6", "col-md-4", "col-xl-3")

        val cityCard = document.createElement("div") as HTMLDivElement
        cityCard.addClass("city-container", "m-1", "card", "card-shadow")
        cityContainer?.appendChild(cityCard)

        val cityCardBody = document.createElement("div") as HTMLDivElement
        cityCardBody.addClass("card-body")
        cityCard.appendChild(cityCardBody)

        nameHolder = document.createElement("h4") as HTMLHeadingElement
        nameHolder?.addClass("city-name")
        cityCardBody.appendChild(nameHolder!!)

        val dataHolder = document.createElement("div") as HTMLDivElement
        dataHolder.addClass("row", "justify-content-center")
        cityCardBody.appendChild(dataHolder)

        loadingIconHolder = document.createElement("img") as HTMLImageElement
        loadingIconHolder?.addClass("city-load-icon")
        loadingIconHolder?.src = "icon/loading.svg"
        dataHolder.appendChild(loadingIconHolder!!)

        weatherIconHolder = document.createElement("img") as HTMLImageElement
        weatherIconHolder?.addClass("city-weather-icon")
        dataHolder.appendChild(weatherIconHolder!!)

        temperatureHolder = document.createElement("span") as HTMLSpanElement
        temperatureHolder?.addClass("city-temperature")
        dataHolder.appendChild(temperatureHolder!!)

        weatherDescriptionHolder = document.createElement("span") as HTMLSpanElement
        weatherDescriptionHolder?.addClass("city-weather-desc")
        cityCardBody.appendChild(weatherDescriptionHolder!!)

        errorMessageHolder = document.createElement("span") as HTMLSpanElement
        errorMessageHolder?.addClass("city-error-message", "row", "justify-content-center")
        cityCardBody.appendChild(errorMessageHolder!!)

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

        weatherIconHolder?.style?.display = if (weatherLoaded && !hasError) "flex" else "none"
        weatherDescriptionHolder?.style?.display = if (weatherLoaded && !hasError) "flex" else "none"
        temperatureHolder?.style?.display = if (weatherLoaded && !hasError) "flex" else "none"
        loadingIconHolder?.style?.display = if (weatherLoaded || hasError) "none" else "flex"
        errorMessageHolder?.style?.display = if (hasError) "flex" else "none"
    }
}