package com.example.gd.presentation.navigation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object WelcomeScreen : Screen("welcome_screen")
    object LoginScreen : Screen("login_screen")
    object CreateAccountScreen : Screen("create_account_screen")
    object OtpVerifyScreen : Screen("otp_verify_screen")
    object ForgotPasswordScreen : Screen("forgot_password_screen")
    object HomeScreen : Screen("home_screen")
    object FavoriteScreen : Screen("favorite_screen")
    object SearchScreen : Screen("search_screen")
    object OrderScreen : Screen("order_screen")
    object TrackOrderScreen : Screen("track_order_screen")
    object SavesScreen : Screen("saves_screen")
    object ProfileScreen : Screen("profile_screen")
    object AdminPanelScreen : Screen("admin_panel_screen")
    object CategoryManageScreen : Screen("category_manage_screen")
    object UserManageScreen : Screen("user_manage_screen")
    object ProductManageScreen : Screen("product_manage_screen")
    object ProductScreen : Screen("product_screen")
    object OrderHistory : Screen("order_history")
    object ManageOrderScreen : Screen("manage_order_screen")
    object SupportScreenUser : Screen("support_screen_user")
    object SupportScreenAdmin : Screen("support_screen_admin")
    object AboutScreen : Screen("about_screen")


}
