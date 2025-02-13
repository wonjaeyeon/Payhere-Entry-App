package com.teclast_korea.payhere_entry.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.teclast_korea.payhere_entry.data.model.InstalledApp




@Composable
fun AppSelectionScreen(
    installedApps: List<InstalledApp>,
    onAppSelected: (String) -> Unit
) {
    LazyColumn {
        items(installedApps) { app ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAppSelected(app.packageName) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1) Convert the Drawable to Bitmap once. Use remember(...) to avoid repeated work.
                val iconBitmap = remember(app.icon) {
                    // requires: implementation "androidx.core:core-ktx:<version>"
                    app.icon.toBitmap()
                }

                // 2) Display the Bitmap
                Image(
                    bitmap = iconBitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = app.packageName)
            }
            HorizontalDivider()
        }
    }
}