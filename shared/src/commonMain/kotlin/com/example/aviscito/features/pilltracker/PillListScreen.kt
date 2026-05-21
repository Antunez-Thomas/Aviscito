package com.example.aviscito.features.pilltracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Plus
import org.koin.compose.koinInject

@Composable
fun PillListScreen(
   viewModel: PillViewModel = koinInject(),
   goHome: () -> Unit = {}
) {
   val state by viewModel.state.collectAsState()
   var showDialog by remember { mutableStateOf(false) }

   PillListScreenContent(
      state = state,
      onEvent = viewModel::handleEvent,
      showDialog = showDialog,
      onShowDialogChange = { showDialog = it },
      goHome = goHome
   )
}

@Composable
fun PillListScreenContent(
   state: PillUiState,
   onEvent: (PillUIEvent) -> Unit,
   showDialog: Boolean,
   onShowDialogChange: (Boolean) -> Unit,
   goHome: () -> Unit = {}
) {
   Scaffold(
      floatingActionButton = {
         FloatingActionButton(onClick = { onShowDialogChange(true) }) {
            Icon(
               imageVector = FeatherIcons.Plus,
               contentDescription = "Add Pill",
            )
         }
      },
      topBar = {
         TopAppBar(
            title = { Text(text = "Pill List") },
            navigationIcon = {
               IconButton(onClick = { goHome() }) {
                  Icon(
                     imageVector = FeatherIcons.ArrowLeft,
                     contentDescription = "Back"
                  )
               }
            }
         )
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
      onShowDialogChange = {},
      goHome = {}
   )
}