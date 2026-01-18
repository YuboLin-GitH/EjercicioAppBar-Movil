package com.example.ejercicioappbar_movil

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.ejercicioappbar_movil.ui.theme.EjercicioAppBarMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjercicioAppBarMovilTheme {
                val context = LocalContext.current

                var verificarCuenta by remember { mutableStateOf(false) }
                var mostrarDialog by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBarMenu(
                            title = "Mi Aplicación",
                            verificarCuenta = verificarCuenta,

                            onNavigationClick = {
                                Toast.makeText(context, "Navegación", Toast.LENGTH_SHORT).show()
                            },
                            onFavoriteClick = {
                                Toast.makeText(context, "Favorito", Toast.LENGTH_SHORT).show()
                            },
                            onMenuItemClick = { option ->
                                Toast.makeText(context, "Menu: $option", Toast.LENGTH_SHORT).show()
                            },
                            onLoginClick = { mostrarDialog = true }
                        )
                    }

                ) { innerPadding ->
                    if (mostrarDialog) {
                        LoginDialog(
                            onDismissRequest = { mostrarDialog = false },
                            loginExito = {
                                verificarCuenta = true
                                Toast.makeText(context, "¡Login correcto!", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                    Text( text = "Cotenedo 123", modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarMenu(
    title: String,
    onNavigationClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    verificarCuenta: Boolean,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier){
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { onNavigationClick() }) {
                Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Navegación")
            }
        },
        title = { Text(text = title) },
        actions = {

                IconButton(onClick = { onFavoriteClick() }) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "Favorito"
                    )
                }

                if (verificarCuenta) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Usuario Login")
                }

                MenuDesplegable(
                    onOptionSelected = onMenuItemClick,
                    onIngresarClick = onLoginClick
                )
        })
}

@Composable
fun MenuDesplegable(onOptionSelected: (String) -> Unit,
                    onIngresarClick: () -> Unit){
    var menu_expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { menu_expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
    }
    DropdownMenu(
        expanded = menu_expanded,
        onDismissRequest = { menu_expanded = false }
    ) {

        DropdownMenuItem(
            text = { Text("Ingresar") },
            onClick = {
                menu_expanded = false
                onIngresarClick()
            }
        )


        DropdownMenuItem(

            text = { Text("Configuración") },
            onClick = {
                menu_expanded = false
                onOptionSelected("Configuración")
            }
        )
        DropdownMenuItem(
            text = { Text("Compartir") },
            onClick = {
                menu_expanded = false
                onOptionSelected("Compartir")
            }
        )

        DropdownMenuItem(
            text = { Text("Cerrar sesión") },
            onClick = {
                menu_expanded = false
                onOptionSelected("Cerrar sesión")
            }
        )
    }
}

@Composable
fun LoginDialog(
    onDismissRequest: () -> Unit,
    loginExito: () -> Unit
) {
    var usuario by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val context = LocalContext.current

    AlertDialog(
        title = { Text("Iniciar Sesión") },
        text = {
            Column {
                TextField(value = usuario, onValueChange = { usuario = it }, label = { Text("Usuario") })
                TextField(value = pass, onValueChange = { pass = it }, label = { Text("Contraseña") })
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                if (usuario == "admin" && pass == "admin") {
                    loginExito()
                    onDismissRequest()
                }else {
                    Toast.makeText(context, "¡error de usuairo o contraseña!", Toast.LENGTH_SHORT).show()
                }
            }) { Text("Entrar") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar") }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjercicioAppBarMovilTheme {
       AppBarMenu(
           title = "Vista Previa",
           onNavigationClick = {},
           onFavoriteClick = {},
           onMenuItemClick = {},
           verificarCuenta = true,
           onLoginClick = {}
       )
    }
}