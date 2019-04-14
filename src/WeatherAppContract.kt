interface WeatherAppContract {
    interface View

    interface Presenter {
        fun addCity(cityName: String?, callback: () -> Unit)
        fun getCitiesWeather(): Collection<LocationWeather>
        fun getAlerts(): Collection<Alert>
        fun cleanAlerts()
    }
}