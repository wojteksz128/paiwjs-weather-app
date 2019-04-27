import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.dom.clear

class WeatherAppPage(private val presenter: WeatherAppContract.Presenter) : WeatherAppContract.View {

    private val citiesWeather = document.getElementById("cities-weather") as HTMLDivElement
    private val pageAlerts = document.getElementById("page-alerts") as HTMLDivElement

    fun show() {
        assignAddButton()
        assignInitialCities()
        refreshAlerts()
    }

    private fun assignAddButton() {
        val button = document.getElementById("city-btn-add") as HTMLButtonElement
        button.addEventListener("click", {
            val input = document.getElementById("city-input") as HTMLInputElement
            presenter.addCity(CityElement(input.value)) { citiesWeather.appendChild(it.prepareElement()) }
            refreshAlerts()
        })
    }

    private fun assignInitialCities() {
        // TODO: Zmień na zależność od cityelement
        presenter.addCity(CityElement("Łódź")) { citiesWeather.appendChild(it.prepareElement()) }
    }

    private fun refreshAlerts() {
        pageAlerts.clear()
        presenter.getAlerts().forEach {
            val alertDiv = document.createElement("div") as HTMLDivElement
            alertDiv.textContent = it.message
            pageAlerts.appendChild(alertDiv)
        }
        presenter.cleanAlerts()
    }
}