package me.fabiansuarez.compose.lab.netflixbasicnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.HomeScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.LoginScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterScreen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterStep2Screen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.screen.RegisterStep3Screen
import me.fabiansuarez.compose.lab.netflixbasicnavigation.ui.theme.NetflixBasicNavigationTheme

// 1. Definición de las rutas del proyecto
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object RegisterStep2 : Screen("register_step2")
    object RegisterStep3 : Screen("register_step3")
    object Home : Screen("home")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NetflixBasicNavigationTheme {
                NetflixAppNavigation()
            }
        }
    }
}

// 2. Control del grafo de navegación
@Composable
fun NetflixAppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla 1: Login
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Pantalla 2: Registro (Paso 1)
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToStep2 = { navController.navigate(Screen.RegisterStep2.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla 3: Registro (Paso 2)
        composable(Screen.RegisterStep2.route) {
            RegisterStep2Screen(
                onNavigateToStep3 = { navController.navigate(Screen.RegisterStep3.route) },
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla 4: Registro (Paso 3)
        composable(Screen.RegisterStep3.route) {
            RegisterStep3Screen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        // Pantalla 5: Home (Menú principal)
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }
}