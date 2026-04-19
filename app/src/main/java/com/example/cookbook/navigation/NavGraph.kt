/**
 * Defines the navigation graph for the CookBook application.
 * Sets up the NavHost and maps destinations to their corresponding Composable screens.
 */
package com.example.cookbook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

import com.example.cookbook.ui.CookBookViewModel
import com.example.cookbook.ui.screens.*

/**
 * The main navigation host for the application.
 */
@Composable
fun CookBookNavGraph(
    navController: NavHostController,
    viewModel: CookBookViewModel,
    modifier: Modifier = Modifier
) {
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
                viewModel = viewModel,
                onEditClick = { id -> navController.navigate(EditRecipe(recipeId = id)) },
                onDeleteSuccess = { navController.popBackStack() }
            )
        }

        composable<EditRecipe> { backStackEntry ->
            val args = backStackEntry.toRoute<EditRecipe>()
            EditRecipeScreen(
                recipeId = args.recipeId,
                viewModel = viewModel,
                onRecipeUpdated = { navController.popBackStack() }
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