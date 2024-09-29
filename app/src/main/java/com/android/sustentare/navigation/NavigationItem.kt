package com.android.sustentare.navigation


sealed class NavigationItem(val route: String) {
    object Login : NavigationItem("login")
    object Signup : NavigationItem("signup")
    object Home : NavigationItem("home")
    object Co2EmissionWork : NavigationItem("co2_emission_work")
    object Educational : NavigationItem("educational")
    object Profile : NavigationItem("profile")
    data class EducationalDetail(val contentLink: String) : NavigationItem("educational_detail/$contentLink") {
        companion object {
            fun createRoute(contentLink: String) = "educational_detail/$contentLink"
        }
    }
}
