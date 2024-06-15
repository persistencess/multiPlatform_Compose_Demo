package ui.services

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

actual object Device {
    @OptIn(ExperimentalForeignApi::class)
    actual fun getBottomSafeAreaHeight(): Double {
        val scene=UIApplication.sharedApplication.connectedScenes.first() as UIWindowScene
        val window=scene.windows.first() as UIWindow
        window.safeAreaInsets.useContents {
            return this.bottom
        }
    }
}