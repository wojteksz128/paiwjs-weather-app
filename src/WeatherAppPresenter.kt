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
            alerts.add(Alert(AlertType.info, "Pogoda w podany mieście jest obecnie wyświetlana."))
            return true
        }
        return false
    }

    private fun hasEmptyName(city: CityElement): Boolean {
        if (city.cityName.isEmpty()) {
            alerts.add(Alert(AlertType.warning, "Prosze wpisać nazwę miasta."))
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

