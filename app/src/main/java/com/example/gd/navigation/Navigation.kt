package com.example.gd.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gd.presentation.*
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.screens.*
import com.example.gd.presentation.screens.bottom.*
import com.example.gd.presentation.screens.login_screen.LoginScreen
import com.example.gd.presentation.screens.order_screen.OrderScreen
import com.example.gd.presentation.screens.signup_screen.CreateAccountScreen
import com.example.gd.presentation.screens.signup_screen.OtpVerifyScreen
import com.example.gd.presentation.screens.splash_screen.SplashScreen
import com.example.gd.presentation.screens.welcome_screen.WelcomeScreen

@Composable
fun Navigation(navController: NavHostController) {

    val authViewModel: AuthenticationViewModel = hiltViewModel()
    //val userViewModel: UserViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController)
        }

        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController, authViewModel)
        }

        composable(
            route = Screen.CreateAccountScreen.route
        ) {
            CreateAccountScreen(navController, authViewModel)
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
            ProfileScreen(navController, authViewModel = authViewModel)
        }
    }
}