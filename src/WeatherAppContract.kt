import org.w3c.dom.HTMLElement

interface WeatherAppContract {
    interface View

    interface Presenter {
        fun addCity(city: CityElement, callback: (CityElement) -> Unit)
        fun getCitiesWeather(): List<CityElement>
        fun getAlerts(): Collection<Alert>
        fun cleanAlerts()
    }

    interface ShowElement {
        fun isInitialized(): Boolean
        fun prepareElement(): HTMLElement
        fun refresh()
    }

    interface AlertElement : ShowElement
}