package com.example.aviscito.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey

class BottomBarBackStack<T : NavKey>(startKey: T) {
    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<NavKey>> = linkedMapOf(
        startKey to mutableStateListOf<NavKey>(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf<NavKey>(startKey)

    private fun updateBackStack() {
        backStack.apply {
            clear()
            addAll(topLevelStacks[topLevelKey] ?: mutableStateListOf())
        }
    }

    fun addTopLevel(key: T) {
        if (topLevelStacks[key] == null) {
            topLevelStacks[key] = mutableStateListOf(key)
        } else {
            val stack = topLevelStacks.remove(key)
            stack?.let { topLevelStacks[key] = it }
        }
        topLevelKey = key
        updateBackStack()
    }

    fun add(key: NavKey) {
        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        val currentTabStack = topLevelStacks[topLevelKey]
        if (currentTabStack?.size!! > 1) {
            currentTabStack.removeLastOrNull()
        } else {
            if (topLevelStacks.size > 1) {
                topLevelStacks.remove(topLevelKey)
                topLevelKey = topLevelStacks.keys.last()
            }
        }
        updateBackStack()
    }
}
