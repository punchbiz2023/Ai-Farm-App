package com.Uma.byldwealth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.Uma.byldwealth.ui.theme.BYLDWealthTheme
import com.Uma.byldwealth.ui.screens.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BYLDWealthTheme {
                NavGraph()
            }
        }
    }
}