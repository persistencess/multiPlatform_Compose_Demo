import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import ui.screen.HomeScreen


@Composable
@Preview
fun App() {
   MaterialTheme{
      HomeScreen()
   }
}