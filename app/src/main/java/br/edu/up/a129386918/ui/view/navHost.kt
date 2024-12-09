package br.edu.up.a129386918.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.up.a129386918.ui.userViewModel

@Composable
fun navHost(
    viewModel: userViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "listarUsers"
    ){
        composable("listarUsers"){
            ListarUserScreen(viewModel, navController)
        }
        composable("addUser"){
            AddUserScreen(viewModel, navController)
        }
        composable("editarUser/{userId}"){ request ->
            val userId = request.arguments?.getString("userId")
            EditUserScreen(viewModel, userId?.toInt(), navController)
        }
    }
}

