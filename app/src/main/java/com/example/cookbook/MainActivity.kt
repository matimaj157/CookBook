package com.example.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cookbook.data.local.AppDatabase
import com.example.cookbook.navigation.CookBookNavGraph
import com.example.cookbook.ui.CookBookViewModel
import com.example.cookbook.ui.CookBookViewModelFactory
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

                // Pobieramy ViewModel, aby uzyskać dostęp do liczby elementów w liście zakupów
                val context = LocalContext.current
                val database = AppDatabase.getDatabase(context)
                val viewModel: CookBookViewModel = viewModel(
                    factory = CookBookViewModelFactory(database.cookbookDao())
                )

                val shoppingList by viewModel.shoppingList.collectAsState(initial = emptyList())
                val shoppingItemCount = shoppingList.size

                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    topBar = { TopBanner() },
                    bottomBar = { BottomBar(navController, shoppingItemCount) }
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