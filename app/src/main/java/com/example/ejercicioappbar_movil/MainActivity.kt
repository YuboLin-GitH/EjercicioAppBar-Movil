package com.example.ejercicioappbar_movil

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppBarMenu(
                            title = "Mi Aplicación",
                            onNavigationClick = {
                                Toast.makeText(context, "Navegación", Toast.LENGTH_SHORT).show()
                            },
                            onFavoriteClick = {
                                Toast.makeText(context, "Favorito", Toast.LENGTH_SHORT).show()
                            },
                            onMenuItemClick = { option ->
                                Toast.makeText(context, "Menu: $option", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                ) { innerPadding ->
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
                MenuDesplegable(onOptionSelected = onMenuItemClick)

        })
}

@Composable
fun MenuDesplegable(onOptionSelected: (String) -> Unit){
    var menu_expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { menu_expanded = true }) {
        Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
    }
    DropdownMenu(
        expanded = menu_expanded,
        onDismissRequest = { menu_expanded = false }
    ) {

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




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjercicioAppBarMovilTheme {
       AppBarMenu(
           title = TODO(),
           onNavigationClick = TODO(),
           onFavoriteClick = TODO(),
           onMenuItemClick = TODO()
       )
    }
}