package com.example.aviscito.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import aviscito.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import aviscito.shared.generated.resources.ic_add

@Composable
fun PillListScreen(viewModel: PillViewModel = koinInject()) {
   val state by viewModel.state.collectAsState()
   var showDialog by remember { mutableStateOf(false) }

   PillListScreenContent(
      state = state,
      onEvent = viewModel::handleEvent,
      showDialog = showDialog,
      onShowDialogChange = { showDialog = it }
   )
}

@Composable
fun PillListScreenContent(
   state: PillUiState,
   onEvent: (PillUIEvent) -> Unit,
   showDialog: Boolean,
   onShowDialogChange: (Boolean) -> Unit
) {
   Scaffold(
      floatingActionButton = {
         FloatingActionButton(onClick = { onShowDialogChange(true) }) {
            Icon(
               painter = painterResource(Res.drawable.ic_add),
               contentDescription = "Add Pill",
            )
         }
      }
   ) { padding ->
      Box(modifier = Modifier
         .padding(padding)
         .fillMaxSize()
      ) {
         when {
            state.isLoading -> {
               CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.error != null -> {
               Text("Error: ${state.error}", modifier = Modifier.align(Alignment.Center))
            }
            state.pills.isEmpty() -> {
               Text(
                  "No pills yet. Tap + to add one.",
                  modifier = Modifier.align(Alignment.Center),
                  style = MaterialTheme.typography.bodyLarge,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
               )
            }
            else -> {
               LazyColumn {
                  items(state.pills) { pill ->
                     PillRow(pill, onEvent)
                  }
               }
            }
         }
      }
   }

   if (showDialog) {
      AddPillDialog(
         onDismiss = { onShowDialogChange(false) },
         onSave = { name, frequency, time ->
            onEvent(PillUIEvent.AddPill(name, frequency, time))
            onShowDialogChange(false)
         }
      )
   }
}
@Preview
@Composable
private fun PillListScreenPreview() {
   PillListScreenContent(
      state = PillUiState(),
      onEvent = {},
      showDialog = false,
      onShowDialogChange = {}
   )
}