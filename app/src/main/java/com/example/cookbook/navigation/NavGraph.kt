package com.example.cookbook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

import com.example.cookbook.data.local.AppDatabase
import com.example.cookbook.ui.CookBookViewModel
import com.example.cookbook.ui.CookBookViewModelFactory
import com.example.cookbook.ui.screens.*

@Composable
fun CookBookNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Pobieramy bazę i tworzymy ViewModel
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val viewModel: CookBookViewModel = viewModel(
        factory = CookBookViewModelFactory(database.cookbookDao())
    )

    NavHost(
        navController = navController,
        startDestination = Welcome,
        modifier = modifier
    ) {
        composable<Welcome> {
            WelcomeScreen(onStartClick = { navController.navigate(RecipesList) })
        }

        composable<RecipesList> {
            RecipesListScreen(
                viewModel = viewModel,
                onRecipeClick = { id -> navController.navigate(RecipeDetails(recipeId = id)) }
            )
        }

        composable<RecipeDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<RecipeDetails>()
            RecipeDetailsScreen(
                recipeId = args.recipeId,
                viewModel = viewModel
            )
        }

        composable<ShoppingList> {
            ShoppingListScreen(viewModel = viewModel)
        }

        composable<AddRecipe> {
            AddRecipeScreen(
                viewModel = viewModel,
                onRecipeAdded = { navController.popBackStack() }
            )
        }

        composable<Pantry> {
            PantryScreen(viewModel = viewModel)
        }
    }
}