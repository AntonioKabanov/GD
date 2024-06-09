package com.example.gd.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gd.domain.model.Product
import com.example.gd.presentation.*
import com.example.gd.presentation.Authentication.AuthenticationViewModel
import com.example.gd.presentation.Categories.CategoryViewModel
import com.example.gd.presentation.Support.SupportScreenAdmin
import com.example.gd.presentation.Support.SupportScreenUser
import com.example.gd.presentation.screens.*
import com.example.gd.presentation.screens.about_screen.AboutScreen
import com.example.gd.presentation.screens.admin_panel.AdminPanelScreen
import com.example.gd.presentation.screens.admin_panel.AdminViewModel
import com.example.gd.presentation.screens.admin_panel.CategoryManageScreen
import com.example.gd.presentation.screens.admin_panel.OrderManageScreen
import com.example.gd.presentation.screens.admin_panel.ProductManageScreen
import com.example.gd.presentation.screens.admin_panel.UserManageScreen
import com.example.gd.presentation.screens.bottom.*
import com.example.gd.presentation.screens.login_screen.LoginScreen
import com.example.gd.presentation.screens.order_screen.OrderHistory
import com.example.gd.presentation.screens.order_screen.OrderScreen
import com.example.gd.presentation.screens.order_screen.TrackOrderScreen
import com.example.gd.presentation.screens.product_screen.ProductScreen
import com.example.gd.presentation.screens.signup_screen.CreateAccountScreen
import com.example.gd.presentation.screens.signup_screen.OtpVerifyScreen
import com.example.gd.presentation.screens.splash_screen.SplashScreen
import com.example.gd.presentation.screens.welcome_screen.WelcomeScreen

@Composable
fun Navigation(navController: NavHostController, checkAlertCount: (Int) -> Unit) {

    val authViewModel: AuthenticationViewModel = hiltViewModel()
    val adminViewModel: AdminViewModel = hiltViewModel()
    var alertCount by remember { mutableIntStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(
            route = Screen.SplashScreen.route
        ) {
            SplashScreen(navController, authViewModel)
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
            HomeScreen(navController, authViewModel = authViewModel)
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
            OrderScreen(
                navController,
                checkAlertCount = {
                    alertCount = it
                    checkAlertCount(alertCount)
                }
            )
        }
        composable(
            route = Screen.SupportScreenUser.route
        ) {
            SupportScreenUser(navController = navController)
        }
        composable(
            route = Screen.SupportScreenAdmin.route
        ) {
            SupportScreenAdmin()
        }
        composable(
            route = Screen.AboutScreen.route
        ) {
            AboutScreen(navController)
        }
        composable(
            route = Screen.TrackOrderScreen.route
        ) {
            val productList: List<Product>? = navController.previousBackStackEntry?.savedStateHandle?.get("productList")
            val countList: List<Int>? = navController.previousBackStackEntry?.savedStateHandle?.get("counterList")
            val price: Double? = navController.previousBackStackEntry?.savedStateHandle?.get("price")
            if (productList != null && countList != null && price != null) {
                TrackOrderScreen(
                    navController = navController,
                    productList = productList,
                    countList = countList,
                    price = price
                )
            }
        }
        composable(
            route = Screen.ProfileScreen.route
        ) {
            ProfileScreen(navController, authViewModel = authViewModel)
        }
        composable(
            route = Screen.AdminPanelScreen.route
        ) {
            AdminPanelScreen(navController, adminViewModel = adminViewModel)
        }
        composable(
            route = Screen.CategoryManageScreen.route
        ) {
            CategoryManageScreen(navController)
        }
        composable(
            route = Screen.ProductManageScreen.route
        ) {
            ProductManageScreen(navController)
        }
        composable(
            route = Screen.UserManageScreen.route
        ) {
            UserManageScreen(navController)
        }
        composable(
            route = Screen.OrderHistory.route
        ) {
            OrderHistory(navController)
        }
        composable(
            route = Screen.ManageOrderScreen.route
        ) {
            OrderManageScreen(navController)
        }
        composable(
            route = Screen.ProductScreen.route
        ) {
            val productObj: Product? = navController.previousBackStackEntry?.savedStateHandle?.get("productItem")
            ProductScreen(navController, productObj!!)
        }
    }
}