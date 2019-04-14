import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import kotlin.browser.document
import kotlin.dom.clear

class WeatherAppPage(private val presenter: WeatherAppContract.Presenter) : WeatherAppContract.View {

    private val citiesWeather = document.getElementById("cities-weather") as HTMLDivElement

    fun show() {
        assignAddButton()
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun assignAddButton() {
        val button = document.getElementById("city-btn-add") as HTMLButtonElement
        button.addEventListener("click", {
            val input = document.getElementById("city-input") as HTMLInputElement
            if (presenter.addCity(input.value)) {
                refreshCitiesWeather()
            } else {
                // TODO: Jeśli nie ma takiego miasta, lub ono istnieje, wyświetl komunikat.
            }
        })
    }

    private fun refreshCitiesWeather() {
        citiesWeather.clear()
        presenter.getCities().forEach {
            val cityDiv = document.createElement("div") as HTMLDivElement
            cityDiv.textContent = it
            citiesWeather.appendChild(cityDiv)
        }
    }
}