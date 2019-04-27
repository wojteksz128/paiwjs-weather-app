import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLSpanElement
import kotlin.browser.document
import kotlin.dom.addClass

data class Alert(val type: AlertType, val message: String) : WeatherAppContract.AlertElement {
    private var alertContainer: HTMLDivElement? = null
    private var messageHolder: HTMLSpanElement? = null

    override fun isInitialized(): Boolean = alertContainer != null

    override fun prepareElement(): HTMLElement {
        if (isInitialized()) {
            throw IllegalStateException("Element is initialized. \n $this")
        }
        alertContainer = document.createElement("div") as HTMLDivElement
        alertContainer?.addClass("alert", "alert-${type.name}", "alert-dismissable", "fade", "show")
        alertContainer?.setAttribute("role", "alert")

        messageHolder = document.createElement("span") as HTMLSpanElement
        alertContainer?.appendChild(messageHolder!!)

        val closeButton = document.createElement("button") as HTMLButtonElement
        closeButton.addClass("close")
        closeButton.setAttribute("data-dismiss", "alert")
        closeButton.setAttribute("aria-label", "Zamknij")
        closeButton.textContent = "\u0215"
        alertContainer?.appendChild(closeButton)

        refresh()
        return alertContainer!!
    }

    override fun refresh() {
        if (!isInitialized()) {
            throw IllegalStateException("Element is not initialized. \n $this")
        }
        messageHolder!!.textContent = message
    }
}

enum class AlertType {
    primary, secondary, success, danger, warning, info, light, dark
}