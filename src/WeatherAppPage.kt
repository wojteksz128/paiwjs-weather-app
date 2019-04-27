import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLFormElement
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
        val form = document.getElementById("city-add-form") as HTMLFormElement
        form.addEventListener("submit", {
            val input = document.getElementById("city-input") as HTMLInputElement
            presenter.addCity(CityElement(input.value)) { citiesWeather.appendChild(it.prepareElement()) }
            input.value = ""
            refreshAlerts()
            it.preventDefault()
            it.stopPropagation()
        })
    }

    private fun assignInitialCities() {
        presenter.addCity(CityElement("Łódź")) { citiesWeather.appendChild(it.prepareElement()) }
    }

    private fun refreshAlerts() {
        pageAlerts.clear()
        presenter.getAlerts().forEach {
            pageAlerts.appendChild(it.prepareElement())
        }
        presenter.cleanAlerts()
    }
}