package com.example.appshortcut

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.example.appshortcut.ui.theme.AppShortcutTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppShortcutTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.CenterVertically
                    )
                ) {
                    when(viewModel.shortcutType) {
                        ShortcutType.STATIC -> Text("Static shortcut clicked")
                        ShortcutType.DYNAMIC -> Text("Dynamic shortcut clicked")
                        ShortcutType.PINNED -> Text("Pinned shortcut clicked")
                        null -> Unit

                    }
                    Button(onClick = ::addDynamicShortcut) {
                        Text("Add dynamic shortcut")
                    }
                }
            }
        }
    }

    private fun addDynamicShortcut()
    {
        val shortcut = ShortcutInfoCompat.Builder(applicationContext,"dynamic")
            .setShortLabel("Call Mum")
            .setLongLabel("Clicking this will call your mum")
            .setIcon(IconCompat.createWithResource(
                applicationContext,R.drawable.baseline_baby_changing_station_24
            ))
            .setIntent(
                Intent(applicationContext, MainActivity::class.java).apply {
                    action=Intent.ACTION_VIEW
                    putExtra("shortcut_id","dynamic")
                }
            )
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(applicationContext,shortcut)
    }
}
