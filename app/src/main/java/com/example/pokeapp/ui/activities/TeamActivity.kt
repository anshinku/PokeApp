package com.example.pokeapp.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.OnStartPokemonFragment
import com.example.pokeapp.model.SpeciesResult
import com.example.pokeapp.ui.fragments.PokemonFragment
import com.example.pokeapp.ui.fragments.TeamFragment
import com.example.pokeapp.viewmodel.PokeDexesViewModel
import constants.ActivityConstants.Extra

class TeamActivity : AppCompatActivity(), OnStartPokemonFragment {
    private var activityResumed: Boolean = false
    private var fragmentTransactionPendingForCommit: Boolean = false
    private var addFragmentToBackStack: Boolean = false
    private var fragmentTransaction: FragmentTransaction? = null
    private lateinit var pokeDexesViewModel: PokeDexesViewModel
    private val dataSource: ArrayList<SpeciesResult> = ArrayList()

    private lateinit var regionName: String
    private lateinit var pokedexesName: String

    override fun onResume() {
        super.onResume()
        activityResumed = true

        if (fragmentTransactionPendingForCommit) {
            commitPendingFragmentTransaction()
            fragmentTransactionPendingForCommit = false
        }
    }

    override fun onPause() {
        super.onPause()
        activityResumed = false
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun commitPendingFragmentTransaction() {
        if (activityResumed) {
            if (addFragmentToBackStack) {
                fragmentTransaction?.addToBackStack(null)
            }
            fragmentTransaction?.commit()
        } else {
            fragmentTransactionPendingForCommit = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        initialiseVariables()
        setup()
    }

    private fun initialiseVariables() {
        regionName = intent.extras?.getString(Extra().regionName) as String
        pokedexesName = intent.extras?.getString(Extra().pokedexesName) as String

        pokeDexesViewModel = ViewModelProvider(this)[PokeDexesViewModel::class.java]

        getPokedexes()
    }

    private fun setup() {
        val fragment = TeamFragment()
        val bundle = Bundle().apply {
            putString(Extra().regionName, regionName.uppercase())
        }

        fragment.arguments = bundle
        fragment.setOnStartPokemonFragmentListener(this)

        replaceFragment(R.id.container, fragment, false)

    }

    private fun openPokemonFragment() {
        val fragment = PokemonFragment()
        val bundle = Bundle().apply {
            putSerializable("pokemons", dataSource)
        }
        fragment.arguments = bundle

        replaceFragment(R.id.container, fragment, true)
    }

    private fun getPokedexes() {
        pokeDexesViewModel.getPokedex(pokedexesName)
        pokeDexesViewModel.pokemonEntries.observe(this) { pokemonEntries ->
            for (pokemon in pokemonEntries) {
                dataSource.add(pokemon.pokemonSpecies)
            }
        }
    }


    private fun replaceFragment(containerId: Int, fragment: Fragment?, addToBackStack: Boolean) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction!!.replace(containerId, fragment!!)
        addFragmentToBackStack = addToBackStack
        commitPendingFragmentTransaction()
    }

    override fun startPokemonFragment() {
        openPokemonFragment()
    }

}