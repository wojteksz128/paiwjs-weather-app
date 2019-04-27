import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class WeatherAppPresenter : WeatherAppContract.Presenter {
    private val cities = mutableListOf<CityElement>()

    private val alerts = mutableListOf<Alert>()

    override fun addCity(city: CityElement, callback: (CityElement) -> Unit) {
        if (hasEmptyName(city) || isInList(city)) return
        cities.add(city)
        callback(city)
    }

    private fun isInList(city: CityElement): Boolean {
        if (city in cities) {
            alerts.add(Alert("info", "City is currently displayed."))
            return true
        }
        return false
    }

    private fun hasEmptyName(city: CityElement): Boolean {
        if (city.cityName.isEmpty()) {
            alerts.add(Alert("warn", "City with specified name not exists."))
            return true
        }
        return false
    }

    override fun getAlerts(): Collection<Alert> {
        return alerts.toList()
    }

    override fun cleanAlerts() {
        alerts.clear()
    }

    override fun getCitiesWeather(): List<CityElement> {
        return cities.toList()
    }
}

data class Alert(val type: String, val message: String) : WeatherAppContract.AlertElement {
    private var alertContainer: HTMLDivElement? = null

    override fun isInitialized(): Boolean = alertContainer != null

    override fun prepareElement(): HTMLElement {
        if (isInitialized()) {
            throw IllegalStateException("Element is initialized. \n $this")
        }
        alertContainer = document.createElement("div") as HTMLDivElement
        refresh()
        return alertContainer!!
    }

    override fun refresh() {
        if (!isInitialized()) {
            throw IllegalStateException("Element is not initialized. \n $this")
        }
        alertContainer!!.textContent = message
    }
}