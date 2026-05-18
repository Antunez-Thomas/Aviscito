package com.example.aviscito.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun AddPillDialog(
    onDismiss: () -> Unit,
    onSave: (name: String, frequency: String, time: String) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableStateOf("") }
    var time by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Pill") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Pill name") })
                OutlinedTextField(value = frequency, onValueChange = { frequency = it }, label = { Text("Frequency") })
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("Time") })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isNotBlank()) {
                    onSave(name, frequency, time)
                    onDismiss()
                }
            }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}