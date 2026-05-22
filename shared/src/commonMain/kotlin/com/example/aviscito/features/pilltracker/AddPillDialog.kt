package com.example.aviscito.features.pilltracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddPillDialog(
    onDismiss: () -> Unit,
    onSave: (name: String, frequency: String, time: String) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableStateOf("") }
    var selectedHour by rememberSaveable { mutableIntStateOf(8) }
    var selectedMinute by rememberSaveable { mutableIntStateOf(0) }
    var isAM by rememberSaveable { mutableStateOf(true) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }

    val displayTime = buildString {
        append(if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour)
        append(":")
        append(selectedMinute.toString().padStart(2, '0'))
        append(if (isAM) " AM" else " PM")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Pill") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Pill name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = frequency,
                    onValueChange = { frequency = it },
                    label = { Text("Frequency (e.g. Daily, Twice Daily)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { showTimePicker = true }
                ) {
                    OutlinedTextField(
                        value = displayTime,
                        onValueChange = { },
                        label = { Text("Reminder Time") },
                        placeholder = { Text("8:00 AM") },
                        readOnly = true,
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    onSave(name, frequency, displayTime)
                    onDismiss()
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )

    if (showTimePicker) {
        TimePickerDialog(
            initialHour = selectedHour,
            initialMinute = selectedMinute,
            initialIsAM = isAM,
            onDismiss = { showTimePicker = false },
            onConfirm = { hour, minute, am ->
                selectedHour = hour
                selectedMinute = minute
                isAM = am
                showTimePicker = false
            }
        )
    }
}

@Composable
private fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    initialIsAM: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int, isAM: Boolean) -> Unit,
) {
    var hour by rememberSaveable { mutableIntStateOf(initialHour) }
    var minute by rememberSaveable { mutableIntStateOf(initialMinute) }
    var isAM by rememberSaveable { mutableStateOf(initialIsAM) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set Reminder Time") },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    NumberPicker(
                        value = hour,
                        range = 1..12,
                        onValueChange = { hour = it },
                        label = "Hour"
                    )

                    Text(
                        text = ":",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    NumberPicker(
                        value = minute,
                        range = 0..59,
                        onValueChange = { minute = it },
                        label = "Min"
                    )

                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        listOf(true, false).forEach { am ->
                            val isSelected = (am && isAM) || (!am && !isAM)
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected)
                                        MaterialTheme.colorScheme.primaryContainer
                                    else
                                        MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier
                                    .padding(vertical = 2.dp)
                                    .clickable { isAM = am }
                            ) {
                                Text(
                                    text = if (am) "AM" else "PM",
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(hour, minute, isAM) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun NumberPicker(
    value: Int,
    range: IntRange,
    onValueChange: (Int) -> Unit,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextButton(onClick = {
            if (value < range.last) onValueChange(value + 1)
            else onValueChange(range.first)
        }) {
            Text("▲")
        }

        Text(
            text = value.toString().padStart(2, '0'),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        TextButton(onClick = {
            if (value > range.first) onValueChange(value - 1)
            else onValueChange(range.last)
        }) {
            Text("▼")
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
