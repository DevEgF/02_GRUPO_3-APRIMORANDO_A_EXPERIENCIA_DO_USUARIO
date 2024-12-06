package com.dev.bernardoslailati.fundamentosandroidapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random
import kotlin.random.nextInt

data class DiceUiState(
    val rolledDiceValue: Int? = null,
    val numberOfRolls: Int = 0
)

class DiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun rollDice() {
       _uiState.update { currentState ->
           currentState.copy(
               rolledDiceValue = Random.nextInt(from = 1, until = 7),
               numberOfRolls = currentState.numberOfRolls + 1
           )
       }
    }
}