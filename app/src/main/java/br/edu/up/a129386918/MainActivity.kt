package br.edu.up.a129386918

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.up.a129386918.dados.repos.IRepo
import br.edu.up.a129386918.dados.repos.RemoteRepo
import br.edu.up.a129386918.ui.userViewModel
import br.edu.up.a129386918.ui.view.navHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val repository: IRepo
            repository = RemoteRepo()
            val viewModel = userViewModel(repository)
            navHost(viewModel)
        }
    }
}