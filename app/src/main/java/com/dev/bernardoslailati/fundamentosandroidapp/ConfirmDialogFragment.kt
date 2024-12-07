package com.dev.bernardoslailati.fundamentosandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dev.bernardoslailati.fundamentosandroidapp.databinding.DialogFragmentConfirmBinding

class ConfirmDialogFragment: DialogFragment() {

    private var _binding: DialogFragmentConfirmBinding? = null
    private val binding get() = _binding!!

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentConfirmBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

}