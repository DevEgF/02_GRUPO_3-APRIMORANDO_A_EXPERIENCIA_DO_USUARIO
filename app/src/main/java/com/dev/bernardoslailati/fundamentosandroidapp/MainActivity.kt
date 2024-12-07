package com.dev.bernardoslailati.fundamentosandroidapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.dev.bernardoslailati.fundamentosandroidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: DiceViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvMainContainer) as? NavHostFragment
        navHostFragment?.navController
    }

    override fun onResume() {
        super.onResume()

        viewModel.uiStateLiveData.observe(this@MainActivity) { uiState ->
            // Update UI elements
            // id do drawable de dado
            uiState.rolledDice1ImgRes?.let { imgRes -> binding.ivRolledDice1.setImageResource(imgRes) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.profile -> {
                Toast.makeText(this@MainActivity, "Perfil", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.help -> {
                Toast.makeText(this@MainActivity, "Ajuda", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                viewModel.uiState.collect { uiState ->
//                    // Update UI elements
//                    // id do drawable de dado
//                    uiState.rolledDice1ImgRes?.let { imgRes -> binding.ivRolledDice1.setImageResource(imgRes) }
//                }
//            }
//        }

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarSettingsMenu)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnRollDice.setOnClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Rodar os dados")
                .setMessage("Deseja realmente jogar os dados?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim") { _, _ ->
                    viewModel.rollDice()
                }
                .setPositiveButtonIcon(
                    AppCompatResources.getDrawable(
                        this@MainActivity,
                        R.drawable.ic_dice_unknown
                    )
                )
                .setNegativeButton("Não") { _, _ -> }
                .setCancelable(false)
                .create()
                .show()

//            ConfirmDialogFragment().show(supportFragmentManager, "confirm_dialog_fragment")

//            ConfirmBottomSheetDialogFragment().show(supportFragmentManager, "confirm_dialog_fragment")
        }

        binding.btnNextFragment.setOnClickListener {
            val customAnim = AnimationUtils.loadAnimation(this@MainActivity, R.anim.custom_anim)
            binding.btnNextFragment.startAnimation(customAnim)

            navController?.currentDestination?.id.let {
                when (it) {
                    R.id.firstFragment -> {
                        navController?.navigate(
                            R.id.action_firstFragment_to_secondFragment,
                            bundleOf("first_arg" to arrayOf("1", "2", "3"))
                        )
                        binding.btnNextFragment.text =
                            getString(R.string.ver_lista_de_jogadas)
                    }

                    R.id.secondFragment -> {
                        navController?.navigate(R.id.action_secondFragment_to_thirdFragment)
                        binding.btnNextFragment.text =
                            getString(R.string.voltar_para_o_primeiro_fragment)
                    }

                    R.id.thirdFragment -> {
                        navController?.navigate(R.id.action_thirdFragment_to_firstFragment)
                        binding.btnNextFragment.text = getString(R.string.ir_para_proxima_tela)
                    }
                }
            }
        }
    }
}