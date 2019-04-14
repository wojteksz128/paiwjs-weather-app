class WeatherAppPresenter : WeatherAppContract.Presenter {
    private val citiesWeather = mutableMapOf<String, LocationWeather>()

    private val alerts = mutableListOf<Alert>()
    private val api = OpenWeatherMapApi()

    override fun addCity(cityName: String?, callback: () -> Unit) {
        if (cityName !is String || cityName.isEmpty()) {
            alerts.add(Alert("warn", "City with specified name not exists."))
            return
        }

        if (citiesWeather.contains(cityName)) {
            alerts.add(Alert("info", "City is currently displayed."))
            return
        }

        api.getWeatherForCity(cityName) {
            citiesWeather[cityName] = it
            callback()
        }
    }

    override fun getAlerts(): Collection<Alert> {
        return alerts.toList()
    }

    override fun cleanAlerts() {
        alerts.clear()
    }

    override fun getCitiesWeather(): Collection<LocationWeather> {
        return citiesWeather.values.toList()
    }
}

data class Alert(val type: String, val message: String)