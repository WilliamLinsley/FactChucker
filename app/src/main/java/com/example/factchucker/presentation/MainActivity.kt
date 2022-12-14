package com.example.factchucker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.factchucker.presentation.category.CategoryJokeScreen
import com.example.factchucker.presentation.joke.JokeScreen
import com.example.factchucker.presentation.ui.theme.FactChuckerTheme
import com.example.factchucker.presentation.ui.theme.background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FactChuckerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "joke_screen"
                    ) {
                        composable(
                            route = "joke_screen"
                        ) {
                            JokeScreen(navController = navController)
                        }
                        composable(
                            route = "category_joke_screen/{category}",
                            arguments = listOf(
                                navArgument("category") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val cat = it.arguments?.getString("category")
                            CategoryJokeScreen(navController = navController, category = cat)
                        }
                    }
                }
            }
        }
    }
}
