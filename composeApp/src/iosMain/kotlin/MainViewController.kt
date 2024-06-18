import androidx.compose.ui.window.ComposeUIViewController
import example.project.commonMain.cache.DatabaseSchema
import service.DatabaseDriverFactory
import service.databaseSchema

fun MainViewController() = ComposeUIViewController {
    databaseSchema = DatabaseSchema(DatabaseDriverFactory().createDriver())
    App()
}