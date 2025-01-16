package com.teclast_korea.payhere_entry.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items

@Composable
fun AppSelectionScreen(
    installedApps: List<String>,
    onAppSelected: (String) -> Unit
) {
    // A simple LazyColumn listing package names
    LazyColumn {
        items(installedApps) { packageName ->
            Text(
                text = packageName,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAppSelected(packageName) }
                    .padding(16.dp)
            )
            Divider()
        }
    }
}