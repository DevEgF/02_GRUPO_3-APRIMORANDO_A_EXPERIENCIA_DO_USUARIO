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
)

class DiceViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    private val _uiStateLiveData = MutableLiveData(DiceUiState())
    val uiStateLiveData: LiveData<DiceUiState> = _uiStateLiveData

    fun rollDice() {
        _uiState.update { currentState ->
            currentState.copy(
                rolledDice1ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                rolledDice2ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                rolledDice3ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                numberOfRolls = currentState.numberOfRolls + 1
            )
        }

        CoroutineScope(Dispatchers.IO).launch {
            _uiStateLiveData.postValue(
                DiceUiState(
                    rolledDice1ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                    rolledDice2ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                    rolledDice3ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
                    numberOfRolls = (_uiStateLiveData.value?.numberOfRolls ?: 0) + 1,
                )
            )
        }

//        _uiStateLiveData.value = DiceUiState(
//            rolledDice1ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
//            rolledDice2ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
//            rolledDice3ImgRes = getDiceImageResource(Random.nextInt(from = 1, until = 7)),
//            numberOfRolls = (_uiStateLiveData.value?.numberOfRolls ?: 0) + 1,
//        )
    }

    private fun getDiceImageResource(diceValue: Int): Int {
        return when (diceValue) {
            1 -> R.drawable.ic_dice_one
            2 -> R.drawable.ic_dice_two
            3 -> R.drawable.ic_dice_three
            4 -> R.drawable.ic_dice_four
            5 -> R.drawable.ic_dice_five
            6 -> R.drawable.ic_dice_six
            else -> R.drawable.ic_dice_unknown
        }
    }
}