package cl.grupo11.mmconecta.model

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val business: String = "",
    val interests: Set<String> = emptySet()
)
