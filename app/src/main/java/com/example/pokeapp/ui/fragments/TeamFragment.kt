package com.example.pokeapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.OnStartPokemonFragment
import constants.ActivityConstants.Extra
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment : Fragment() {
    private var context = AppCompatActivity()
    private var onStartPokemonFragment: OnStartPokemonFragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as AppCompatActivity
    }

    override fun onResume() {
        super.onResume()
        setOnclickListeners()
    }

    override fun onPause() {
        super.onPause()
        unSetListeners()
    }

    private fun unSetListeners() {
        createTeam.setOnClickListener(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseVariable()
        setup()
    }

    private fun initialiseVariable() {
        regionNameTextView.text = arguments?.getString(Extra().regionName).toString()
    }

    private fun setup() {
    }

    fun setOnStartPokemonFragmentListener(onStartPokemonFragment: OnStartPokemonFragment) {
        this.onStartPokemonFragment = onStartPokemonFragment
    }

    private fun setOnclickListeners() {
        createTeam.setOnClickListener {
            onStartPokemonFragment?.startPokemonFragment()
        }
    }


}