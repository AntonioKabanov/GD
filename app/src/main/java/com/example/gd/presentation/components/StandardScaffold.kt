package com.example.gd.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gd.domain.model.BottomNavItem
import com.example.gd.presentation.navigation.Screen
import com.example.gd.ui.theme.colorRedDark
import com.example.gd.ui.theme.colorWhite

@Composable
fun StandardScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    alertCount: Int,
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    //var alertCount by remember { mutableIntStateOf(0) }

   val bottomNavItems: List<BottomNavItem> = listOf(
    BottomNavItem(
        route = Screen.HomeScreen.route,
        icon = Icons.Outlined.Home,
        contentDescription = "Home"
    ),
    BottomNavItem(
        route = Screen.FavoriteScreen.route,
        icon = Icons.Outlined.Favorite,
        contentDescription = "Favorite"
    ),
    BottomNavItem(route = ""),
    BottomNavItem(
        route = Screen.OrderScreen.route,
        icon = Icons.Outlined.Lock,
        contentDescription = "Order",
        alertCount = alertCount
    ),
    BottomNavItem(
        route = Screen.ProfileScreen.route,
        icon = Icons.Outlined.Person,
        contentDescription = "Profile"
    )
   )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surface,
                    cutoutShape = CircleShape,
                    elevation = 0.dp
                ) {
                    BottomNavigation(backgroundColor = colorWhite) {

                        bottomNavItems.forEachIndexed { i, bottomNavItem ->
                            StandardBottomNavItem(
                                icon = bottomNavItem.icon,
                                contentDescription = bottomNavItem.contentDescription,
                                selected = bottomNavItem.route == navController.currentDestination?.route,
                                enabled = bottomNavItem.icon != null,
                                alertCount = bottomNavItem.alertCount
                            ) {
                                if (navController.currentDestination?.route != bottomNavItem.route) {
                                    navController.navigate(bottomNavItem.route)
                                }
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    backgroundColor = colorRedDark,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = colorWhite
                    )
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center,
        modifier = modifier
    ) {
        content()
    }
}