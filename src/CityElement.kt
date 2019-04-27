import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

data class CityElement(val cityName: String) : WeatherAppContract.ShowElement {
    private var cityContainer: HTMLDivElement? = null
    private lateinit var weather: LocationWeather

    override fun isInitialized() = cityContainer != null

    override fun prepareElement(): HTMLElement {
        if (isInitialized()) {
            throw IllegalStateException("Element is initialized. \n $this")
        }
        cityContainer = document.createElement("div") as HTMLDivElement
        refresh()
        return cityContainer!!
    }

    override fun refresh() {
        if (!isInitialized()) {
            throw IllegalStateException("Element is not initialized. \n$this")
        }
        fillElement(false)
        OpenWeatherMapApi.getWeatherForCity(cityName) {
            weather = it
            fillElement(true)
        }
    }

    private fun fillElement(weatherLoaded: Boolean) {
        cityContainer!!.textContent = if (weatherLoaded) weather.name else cityName
    }
}