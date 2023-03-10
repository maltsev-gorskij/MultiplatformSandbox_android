package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.nav_graphs.RootNavGraph
import ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.theme.SandboxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SandboxTheme {
                RootNavGraph()
            }
        }
    }
}
