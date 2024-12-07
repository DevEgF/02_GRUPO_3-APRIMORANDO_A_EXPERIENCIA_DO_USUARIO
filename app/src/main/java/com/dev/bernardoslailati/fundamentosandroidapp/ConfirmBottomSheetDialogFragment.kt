package com.dev.bernardoslailati.fundamentosandroidapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.bernardoslailati.fundamentosandroidapp.databinding.DialogFragmentConfirmBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConfirmBottomSheetDialogFragment: BottomSheetDialogFragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // ...
        }
    }

}