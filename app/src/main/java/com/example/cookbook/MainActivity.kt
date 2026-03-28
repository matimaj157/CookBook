package com.example.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cookbook.navigation.CookBookNavGraph
import com.example.cookbook.ui.components.BottomBar
import com.example.cookbook.ui.components.TopBanner
import com.example.cookbook.ui.theme.CookBookTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CookBookTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = { TopBanner() },
                    bottomBar = { BottomBar(navController) }
                ) { innerPadding ->
                    CookBookNavGraph(
                        navController = navController,
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }
}