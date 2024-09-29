package com.android.sustentare.navigation


sealed class NavigationItem(val route: String) {
    data object Login : NavigationItem("login")
    data object Signup : NavigationItem("signup")
    data object Home : NavigationItem("home")
    data object Co2EmissionWork : NavigationItem("co2_emission_work")
    data object Educational : NavigationItem("educational")
    data object Profile : NavigationItem("profile")
    data object Challenger: NavigationItem("challenger")
    data class EducationalDetail(val contentLink: String) : NavigationItem("educational_detail/$contentLink") {
        companion object {
            fun createRoute(contentLink: String) = "educational_detail/$contentLink"
        }
    }
    object ChallengerDetail : NavigationItem("challenger_detail/{topicoId}") {
        fun createRoute(topicoId: Int) = "challenger_detail/$topicoId"
    }
}