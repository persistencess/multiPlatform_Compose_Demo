import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screen.HomeScreen


@Composable
@Preview
fun App() {
    MaterialTheme {
        Navigator(HomeScreen) {
            SlideTransition(it)
        }
    }
}