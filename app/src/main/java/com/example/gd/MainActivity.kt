package com.example.gd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gd.navigation.Navigation
import com.example.gd.navigation.Screen
import com.example.gd.presentation.components.StandardScaffold
import com.example.gd.ui.theme.GDTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GDTheme {
                GDComposeUIMain()
            }
        }
    }

    @Composable
    fun GDComposeUIMain() {
        GDTheme {
            androidx.compose.material.Surface(
                color = androidx.compose.material.MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize()
            ) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                StandardScaffold(
                    navController = navController,
                    showBottomBar = navBackStackEntry?.destination?.route in listOf(
                        Screen.HomeScreen.route,
                        Screen.FavoriteScreen.route,
                        Screen.OrderScreen.route,
                        Screen.ProfileScreen.route,
                    ),
                    modifier = Modifier.fillMaxSize(),
                    onFabClick = {
                        navController.navigate(Screen.SearchScreen.route)
                    }
                ) {
                    Navigation(navController)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    GDTheme {
    }
}