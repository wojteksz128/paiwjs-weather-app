fun main() {
    val weatherAppPresenter = WeatherAppPresenter()
    val weatherAppPage = WeatherAppPage(weatherAppPresenter)
    weatherAppPage.show()
}