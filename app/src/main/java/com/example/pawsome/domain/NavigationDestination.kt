//package com.example.pawsome.domain
//
//import com.example.pawsome.model.Screen
//
//sealed class Destination(protected val route: String, vararg params: String) {
//    val fullRoute: String = if (params.isEmpty()) route else {
//        val builder = StringBuilder(route)
//        params.forEach { builder.append("{/$it}") }
//        builder.toString()
//    }
//
//    sealed class NoArgDestination(route: String): Destination(route) {
//        operator fun invoke(): String = route
//    }
//
//    object Register : NoArgDestination("register") {
//        object Login : NoArgDestination("login")
//        object Signup : NoArgDestination("signup")
//    }
//
//    object HomeScreen: NoArgDestination("home")
//    object SettingScreen: Destination("setting", "firstname", "lastname") {
//        const val FIRST_NAME_KEY = "firstname"
//        const val LAST_NAME_KEY = "lastname"
//
//        operator fun invoke(firstname: String, lastname: String): String = route.appendParams(
//            FIRST_NAME_KEY to firstname,
//            LAST_NAME_KEY to lastname
//        )
//    }
//    object AboutUsScreen: NoArgDestination("aboutus")
//    object DetailScreen: NoArgDestination("detail")
//    object MapScreen: NoArgDestination("map")
//    object ChatScreen: NoArgDestination("chat")
//}
//
//internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
//    val builder = StringBuilder(this)
//
//    params.forEach {
//        it.second?.toString()?.let { arg ->
//            builder.append("/$arg")
//        }
//    }
//
//    return builder.toString()
//}