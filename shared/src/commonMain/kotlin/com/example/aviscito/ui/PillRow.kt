package com.example.aviscito.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.aviscito.data.PillEntity

@Composable
fun PillRow(pill: PillEntity, onEvent: (PillUIEvent) -> Unit) {
   val isTaken = pill.takenAt != null

    Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isTaken)
                MaterialTheme.colorScheme.surfaceVariant
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .alpha(if (isTaken) 0.5f else 1f)
            ) {
                Text(pill.name, style = MaterialTheme.typography.titleMedium)
                Text("${pill.frequency} ${pill.time}", style = MaterialTheme.typography.bodySmall)
                if (isTaken) {
                    Text("✓ Taken", color = MaterialTheme.colorScheme.primary)
                }
            }
            Button(onClick = {
                if (isTaken) onEvent(PillUIEvent.MarkAsNotTaken(pill.id))
                else onEvent(PillUIEvent.MarkAsTaken(pill.id))
            }) {
                Text(if(isTaken) "Undo" else "Take")
            }
        }
    }
}