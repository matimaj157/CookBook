package com.example.cookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cookbook.data.local.AppDatabase
import com.example.cookbook.navigation.CookBookNavGraph
import com.example.cookbook.navigation.RecipesList
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
                val view = LocalView.current
                LaunchedEffect(Unit) {
                    if (!view.isSoundEffectsEnabled) {
                        view.isSoundEffectsEnabled = true
                    }
                }
                val navController = rememberNavController()
                val context = LocalContext.current
                val database = AppDatabase.getDatabase(context)
                val viewModel: CookBookViewModel = viewModel(
                    factory = CookBookViewModelFactory(database.cookbookDao())
                )

                val shoppingList by viewModel.shoppingList.collectAsState(initial = emptyList())
                val shoppingItemCount = shoppingList.size

                val navBackStackEntry by navController.currentBackStackEntryAsState()

                val isRecipesList = navBackStackEntry?.destination?.hasRoute<RecipesList>() == true
                
                val searchQuery by viewModel.searchQuery.collectAsState()

                LaunchedEffect(isRecipesList) {
                    if (!isRecipesList) {
                        viewModel.updateSearchQuery("")
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { 
                        TopBanner(
                            showSearchBar = isRecipesList,
                            searchQuery = searchQuery,
                            onSearchQueryChange = {
                                viewModel.updateSearchQuery(it.replace("\n", "")) 
                            }
                        ) 
                    },
                    bottomBar = { BottomBar(navController, shoppingItemCount) }
                ) { innerPadding ->
                    CookBookNavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
