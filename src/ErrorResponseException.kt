data class ErrorResponseException(val response: ErrorResponse) : RuntimeException("Error ${response.cod}: ${response.message}")
