class WeatherAppPresenter : WeatherAppContract.Presenter {

    private val cities = mutableListOf<String>()

    override fun addCity(cityName: String?): Boolean {
        return if (cityName is String && cityName.isNotEmpty()) {
            cities.add(cityName)
            true
        } else {
            false
        }
    }

    override fun getCities(): Collection<String> {
        return cities.toList()
    }
}