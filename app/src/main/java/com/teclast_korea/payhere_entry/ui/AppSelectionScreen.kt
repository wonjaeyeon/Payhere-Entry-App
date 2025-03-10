package com.teclast_korea.payhere_entry.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.graphics.drawable.toBitmap
import com.teclast_korea.payhere_entry.data.model.InstalledApp

// V2
@Composable
fun AppSelectionScreen(
    payhereApps: List<InstalledApp>,
    otherApps: List<InstalledApp>,
    onAppSelected: (String) -> Unit
) {
    val context = LocalContext.current

    val payhereIconDrawable = remember {
        context.packageManager.getApplicationIcon("com.teclast_korea.payhere_entry")
    }
    val payhereIconBitmap = remember(payhereIconDrawable) {
        payhereIconDrawable.toBitmap()
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // -- Title (header) --
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
                text = "Payhere 매장의 새로운 미래",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        // -- Subtitle --
        Text(
            text = "Select the home app",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        // -- Payhere Apps: large tiles that become the new home app when clicked --
        if (payhereApps.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                payhereApps.forEach { app ->
                    PayhereAppTile(
                        app = app,
                        onAppSelected = onAppSelected // <— This sets it as home
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()

        // -- Other Apps: show in a grid, but just launch them without setting as home --
        if (otherApps.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No apps found")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(otherApps) { app ->
                    val iconBitmap = remember(app.icon) { app.icon.toBitmap() }

                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                // Directly launch the app, do NOT call onAppSelected
                                val launchIntent = context.packageManager
                                    .getLaunchIntentForPackage(app.packageName)
                                if (launchIntent != null) {
                                    context.startActivity(launchIntent)
                                }
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            bitmap = iconBitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = app.appName,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PayhereAppTile(
    app: InstalledApp,
    onAppSelected: (String) -> Unit
) {
    val iconBitmap = remember(app.icon) { app.icon.toBitmap() }

    Column(
        modifier = Modifier
            .clickable { onAppSelected(app.packageName) }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            bitmap = iconBitmap.asImageBitmap(),
            contentDescription = "Payhere App Icon",
            modifier = Modifier.size(90.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = app.appName,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
    }
}