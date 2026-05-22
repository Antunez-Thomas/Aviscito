package com.example.aviscito.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import aviscito.shared.generated.resources.Res
import aviscito.shared.generated.resources.panda_logo
import compose.icons.FeatherIcons
import com.example.aviscito.data.toDisplayTime
import compose.icons.feathericons.Heart
import compose.icons.feathericons.Moon
import compose.icons.feathericons.Settings
import compose.icons.feathericons.Sun
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onNavigateToPills: () -> Unit,
    viewModel: HomeViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()

    val groupedPills = state.pendingPills.groupBy { pill ->
        val hour = pill.time / 60
        when (hour) {
            in 5..11 -> "Morning"
            in 12..16 -> "Afternoon"
            else -> "Evening"
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.panda_logo),
                            contentDescription = "Aviscito logo",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Text(
                        text = "Aviscito",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = FeatherIcons.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "My Meds",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Manage your daily schedule and medication details.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (state.pendingPills.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No pending medications.\nTap + to add one.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    listOf("Morning", "Afternoon", "Evening").forEach { category ->
                        val pillsInCategory = groupedPills[category].orEmpty()
                        if (pillsInCategory.isNotEmpty()) {
                            item {
                                CategoryHeader(
                                    icon = when (category) {
                                        "Morning" -> FeatherIcons.Sun
                                        "Evening" -> FeatherIcons.Moon
                                        else -> FeatherIcons.Sun
                                    },
                                    label = category
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            items(pillsInCategory) { pill ->
                                MedicationCard(
                                    name = pill.name,
                                    dosage = pill.frequency,
                                    time = pill.time.toDisplayTime(),
                                    status = MedicationStatuses.Upcoming,
                                    icon = FeatherIcons.Heart
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        ExtendedFloatingActionButton(
            onClick = onNavigateToPills,
            icon = {
                Icon(
                    imageVector = FeatherIcons.Heart,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            },
            text = { Text("Add pill") }
        )
    }
}

@Composable
private fun CategoryHeader(icon: ImageVector, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
