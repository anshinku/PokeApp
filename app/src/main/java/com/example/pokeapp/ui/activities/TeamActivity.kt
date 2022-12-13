package com.example.pokeapp.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.interfacefragment.OnBackPressedFragment
import com.example.pokeapp.interfaces.interfacefragment.OnStartPokemonFragment
import com.example.pokeapp.ui.fragments.PokemonFragment
import com.example.pokeapp.ui.fragments.TeamFragment
import constants.ActivityConstants.Extra
import kotlinx.android.synthetic.main.fragment_pokemon.*

class TeamActivity : AppCompatActivity(), OnStartPokemonFragment, OnBackPressedFragment {
    private var activityResumed: Boolean = false
    private var fragmentTransactionPendingForCommit: Boolean = false
    private var addFragmentToBackStack: Boolean = false
    private var fragmentTransaction: FragmentTransaction? = null
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
            finish()
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
        regionName = intent.extras?.getString(Extra().REGION_NAME) as String
        pokedexesName = intent.extras?.getString(Extra().POKEDEXES_NAME) as String
    }

    private fun setup() {
        val fragment = TeamFragment()
        val bundle = Bundle().apply {
            putString(Extra().REGION_NAME, regionName.uppercase())
        }

        fragment.arguments = bundle
        fragment.setOnStartPokemonFragmentListener(this)

        replaceFragment(R.id.container, fragment, false)

    }

    private fun openPokemonFragment() {
        val fragment = PokemonFragment()
        val bundle = Bundle().apply {
            putString(Extra().POKEDEXES_NAME, pokedexesName)
        }
        fragment.arguments = bundle
        fragment.setOnBackPressedListener(this)
        replaceFragment(R.id.container, fragment, true)
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

    override fun backPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

}