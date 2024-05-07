package com.example.gd.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gd.presentation.*
import com.example.gd.presentation.screens.*
import com.example.gd.presentation.screens.bottom.*
import com.example.gd.presentation.screens.login_screen.LoginScreen
import com.example.gd.presentation.screens.order_screen.OrderScreen
import com.example.gd.presentation.screens.signup_screen.CreateAccountScreen
import com.example.gd.presentation.screens.signup_screen.OtpVerifyScreen
import com.example.gd.presentation.screens.welcome_screen.WelcomeScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = Screen.WelcomeScreen.route
        ) {
            WelcomeScreen(navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController)
        }

        composable(
            route = Screen.CreateAccountScreen.route
        ) {
            CreateAccountScreen(navController)
        }

        composable(
            route = Screen.OtpVerifyScreen.route
        ) {
            OtpVerifyScreen(navController)
        }
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.FavoriteScreen.route
        ) {
            FavoriteScreen(navController)
        }
        composable(
            route = Screen.SearchScreen.route
        ) {
            SearchScreen(navController)
        }
        composable(
            route = Screen.OrderScreen.route
        ) {
            OrderScreen(navController)
        }

        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController)
        }
    }
}