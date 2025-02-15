package com.teclast_korea.payhere_entry.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.graphics.drawable.toBitmap
import com.teclast_korea.payhere_entry.data.model.InstalledApp


@Composable
fun AppSelectionScreen(
    installedApps: List<InstalledApp>,
    onAppSelected: (String) -> Unit
) {

    // 1) Get your own app's icon as a Bitmap
    val context = LocalContext.current
    val payhereIconDrawable = remember {
        // If you specifically want "com.teclast_korea.payhere_entry" icon,
        // use that package name. Otherwise, you can use context.packageName
        // if this code is running in the payhere_entry app.
        context.packageManager.getApplicationIcon("com.teclast_korea.payhere_entry")
    }
    val payhereIconBitmap = remember(payhereIconDrawable) {
        payhereIconDrawable.toBitmap()
    }


    Column {
        // Title: Bold with Icon
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                bitmap = payhereIconBitmap.asImageBitmap(),
                contentDescription = "Payhere Entry Icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Payhere Entry App",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // Subtitle: Smaller font & gray color
        Text(
            text = "Select the home app",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray
            ),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        if (installedApps.isEmpty()) {
            // Center a "No apps found" message
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No apps found")
            }
        } else {
            // Show the LazyColumn list
            LazyColumn {
                items(installedApps) { app ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAppSelected(app.packageName) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Convert the Drawable to Bitmap once
                        val iconBitmap = remember(app.icon) {
                            app.icon.toBitmap()
                        }

                        // Display the Bitmap
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
    }
}