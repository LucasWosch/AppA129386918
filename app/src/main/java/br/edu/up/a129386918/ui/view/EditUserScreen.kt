package br.edu.up.a129386918.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.up.a129386918.dados.models.User
import br.edu.up.a129386918.ui.userViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    viewModel: userViewModel,
    userId: Int?,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var user: User? by remember { mutableStateOf(null) }
    var nomeError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    // Carrega os dados do usuário ao iniciar a tela
    LaunchedEffect(userId) {
        if (userId != null) {
            user = viewModel.buscarUserPorId(userId)
            user?.let {
                nome = it.nome
                email = it.email
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Usuário", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Editar Usuário",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp
            )

            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    nomeError = it.isBlank()
                },
                label = { Text("Nome") },
                isError = nomeError,
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth()
            )
            if (nomeError) {
                Text(
                    text = "O nome não pode estar vazio.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = it.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                label = { Text("E-mail") },
                isError = emailError,
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text(
                    text = "Digite um e-mail válido.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    if (nome.isNotBlank() && email.isNotBlank() && !emailError) {
                        coroutineScope.launch {
                            val userEditado = User(
                                id = userId,
                                nome = nome,
                                email = email
                            )
                            viewModel.gravarUser(userEditado)
                            navController.popBackStack()
                        }
                    } else {
                        nomeError = nome.isBlank()
                        emailError = email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = nome.isNotBlank() && email.isNotBlank() && !emailError
            ) {
                Text(text = "Salvar", fontSize = 18.sp)
            }
        }
    }
}
