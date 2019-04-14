class WeatherAppPresenter : WeatherAppContract.Presenter {
    private val cities = mutableListOf<String>()

    private val alerts = mutableListOf<Alert>()

    override fun addCity(cityName: String?): Boolean {
        return if (cityName is String && cityName.isNotEmpty()) {
            if (!cities.contains(cityName)) {
                cities.add(cityName)
                true
            } else {
                alerts.add(Alert("info", "City is currently displayed."))
                false
            }
        } else {
            alerts.add(Alert("warn", "City with specified name not exists."))
            false
        }
    }
    override fun getAlerts(): Collection<Alert> {
        return alerts.toList()
    }

    override fun cleanAlerts() {
        alerts.clear()
    }

    override fun getCities(): Collection<String> {
        return cities.toList()
    }
}

data class Alert(val type: String, val message: String)