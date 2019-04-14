interface WeatherAppContract {
    interface View

    interface Presenter {
        fun addCity(cityName: String?): Boolean
        fun getCities(): Collection<String>
        fun getAlerts(): Collection<Alert>
        fun cleanAlerts()
    }
}