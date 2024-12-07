package com.dev.bernardoslailati.fundamentosandroidapp

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class DiceUiState(
    @DrawableRes val rolledDice1ImgRes: Int? = null,
    @DrawableRes val rolledDice2ImgRes: Int? = null,
    @DrawableRes val rolledDice3ImgRes: Int? = null,
    val numberOfRolls: Int = 0,
    val rolledDicesList: List<RolledDices> = emptyList()
)

class DiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    private val _uiStateLiveData = MutableLiveData(DiceUiState())
    val uiStateLiveData: LiveData<DiceUiState> = _uiStateLiveData

    fun rollDice() {
        val dice1Value = Random.nextInt(
            from = 1,
            until = 7
        )
        val dice2Value = Random.nextInt(
            from = 1,
            until = 7
        )
        val dice3Value = Random.nextInt(
            from = 1,
            until = 7
        )
        val rolledDices =
            RolledDices(dice1 = dice1Value, dice2 = dice2Value, dice3 = dice3Value)

        _uiState.update { currentState ->
            val currentRolledDicesList = currentState.rolledDicesList.toMutableList()
            currentRolledDicesList.add(rolledDices)
            val updatedRolledDicesList = currentRolledDicesList.toList()

            currentState.copy(
                rolledDice1ImgRes = getDiceImageResource(
                    diceValue = dice1Value
                ),
                rolledDice2ImgRes = getDiceImageResource(
                    diceValue = dice2Value
                ),
                rolledDice3ImgRes = getDiceImageResource(
                    diceValue = dice3Value
                ),
                rolledDicesList = updatedRolledDicesList,
                numberOfRolls = currentState.numberOfRolls + 1,
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            val currentRolledDicesList =
                _uiStateLiveData.value?.rolledDicesList?.toMutableList().orEmpty().toMutableList()
            currentRolledDicesList.add(rolledDices)
            val updatedRolledDicesList = currentRolledDicesList.toList()

            _uiStateLiveData.postValue(
                DiceUiState(
                    rolledDice1ImgRes = getDiceImageResource(diceValue = dice1Value),
                    rolledDice2ImgRes = getDiceImageResource(diceValue = dice2Value),
                    rolledDice3ImgRes = getDiceImageResource(diceValue = dice3Value),
                    rolledDicesList = updatedRolledDicesList,
                    numberOfRolls = (_uiStateLiveData.value?.numberOfRolls ?: 0) + 1,
                )
            )
        }

//        _uiStateLiveData.value = DiceUiState(
//            rolledDice1ImgRes = getDiceImageResource(
//                diceValue = Random.nextInt(
//                    from = 1,
//                    until = 7
//                )
//            ),
//            rolledDice2ImgRes = getDiceImageResource(
//                diceValue = Random.nextInt(
//                    from = 1,
//                    until = 7
//                )
//            ),
//            rolledDice3ImgRes = getDiceImageResource(
//                diceValue = Random.nextInt(
//                    from = 1,
//                    until = 7
//                )
//            ),
//            numberOfRolls = (_uiStateLiveData.value?.numberOfRolls ?: 0) + 1,
//        )
    }

}