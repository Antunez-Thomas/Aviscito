package com.example.aviscito.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import aviscito.shared.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import aviscito.shared.generated.resources.ic_add

@Composable
fun PillListScreen(viewModel: PillViewModel = koinInject()){
   val pills by viewModel.pills.collectAsState()
   var showDialog by remember { mutableStateOf(false) }

   Scaffold(
      floatingActionButton = {
         FloatingActionButton(onClick = { showDialog = true }) {
            Icon(painter = painterResource(Res.drawable.ic_add), contentDescription = null)
         }
      }
   ) { padding ->
      LazyColumn(modifer = Modifier.padding(padding)) {
         items(pills) { pill ->
            PillRow(pill, viewModel)
         }
      }
   }
   if (showDialog) {
      AddPillDialog(
         onDismiss = { showDialog = false },
         onSave = { name, frequency, time ->
            viewModel.addPill(name, frequency, time)
         }
      )
   }
}