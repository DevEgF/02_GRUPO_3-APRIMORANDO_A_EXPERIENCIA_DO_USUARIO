package com.dev.bernardoslailati.fundamentosandroidapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.bernardoslailati.fundamentosandroidapp.databinding.ItemRolledDicesBinding

data class RolledDices(
    val dice1: Int,
    val dice2: Int,
    val dice3: Int,
)

class RolledDicesAdapter(private val rolledDicesList: List<RolledDices> = emptyList()) :
    RecyclerView.Adapter<RolledDicesAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemRolledDicesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playNumber: Int, rolledDices: RolledDices) {
            with(binding) {
                tvRolledDiceNumber.text = playNumber.toString()
                ivRolledDice1.setImageResource(getDiceImageResource(rolledDices.dice1))
                ivRolledDice2.setImageResource(getDiceImageResource(rolledDices.dice2))
                ivRolledDice3.setImageResource(getDiceImageResource(rolledDices.dice3))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            binding = ItemRolledDicesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return rolledDicesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(playNumber = position + 1, rolledDices = rolledDicesList[position])
    }

}