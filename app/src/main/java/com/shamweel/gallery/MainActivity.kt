package com.shamweel.gallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.shamweel.gallery.core.common.utils.PermissionUtils
import com.shamweel.gallery.core.designsystem.theme.GalleryTheme
import com.shamweel.gallery.ui.App
import com.shamweel.ui.NoPermissionComponent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            installSplashScreen()
            enableEdgeToEdge()
            setContent {
                GalleryTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        var isPermissionGranted by remember { mutableStateOf(PermissionUtils.isPermissionsGranted(this)) }

                        val launcher = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.RequestMultiplePermissions(),
                            onResult = { isGranted ->
                                isPermissionGranted = PermissionUtils.isPermissionsGranted(this)
                            }
                        )

                        if (isPermissionGranted) {
                            App()
                        } else {
                            NoPermissionComponent {
                                launcher.launch(PermissionUtils.requiredPermissions)
                            }
                        }
                    }
                }
            }
        }
    }
}