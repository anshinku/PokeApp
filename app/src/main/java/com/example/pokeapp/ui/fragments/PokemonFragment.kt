package com.example.pokeapp.ui.fragments

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.interfacefragment.OnBackPressedFragment
import com.example.pokeapp.model.Pokemons
import com.example.pokeapp.model.TeamPokemon
import com.example.pokeapp.ui.adapters.PokemonAdapter
import com.example.pokeapp.viewmodel.PokeDexesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import constants.ActivityConstants.Extra
import kotlinx.android.synthetic.main.fragment_pokemon.*


class PokemonFragment : Fragment() {
    private var context = AppCompatActivity()
    private lateinit var pokedexesName: String
    private lateinit var regionName: String
    private lateinit var adapter: PokemonAdapter
    private val db = FirebaseDatabase.getInstance()
    private var onBackPressedFragment: OnBackPressedFragment? = null
    private val pokedexesViewModel: PokeDexesViewModel by lazy {
        ViewModelProvider(this)[PokeDexesViewModel::class.java]
    }

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseVariable()
        initObserver()
        setup()
    }

    private fun initObserver() {
        pokedexesViewModel.getAllPokemon(pokedexesName)
        pokedexesViewModel.isLoading.observe(context) {
            val loader = shimmerFrameLayoutPokemon.isShimmerStarted == it
            val loaderS = shimmerFrameLayoutPokemonSecond.isShimmerStarted == it
            shimmerFrameLayoutPokemon.showShimmer(loader)
            shimmerFrameLayoutPokemonSecond.showShimmer(loaderS)
        }

        pokedexesViewModel.pokemons.observe(context) { pokemonList ->
            shimmerFrameLayoutPokemon.isShimmerStarted
            shimmerFrameLayoutPokemon.stopShimmer()
            shimmerFrameLayoutPokemon.visibility = View.GONE

            shimmerFrameLayoutPokemonSecond.isShimmerStarted
            shimmerFrameLayoutPokemonSecond.stopShimmer()
            shimmerFrameLayoutPokemonSecond.visibility = View.GONE

            pokemonList?.let { adapter.updatePokemonList(it) }

        }


    }

    private fun initialiseVariable() {
        pokedexesName = arguments?.getString(Extra().POKEDEXES_NAME).toString()
        regionName = arguments?.getString(Extra().REGION_NAME).toString()

    }

    private fun setup() {

        pokemonView!!.setHasFixedSize(true)
        pokemonView!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = PokemonAdapter(context, pokemonView, "")

        pokemonView.adapter = adapter

    }

    private fun setOnclickListeners() {
        sendData.setOnClickListener {
            val pokemonTeam = adapter.teamPokemon()
            if (pokemonTeam.size == 0) {
                return@setOnClickListener
            } else if (pokemonTeam.size < 3) {
                warningDialog(getString(R.string.message_pokemon))
            } else if (pokemonTeam.size > 6) {
                warningDialog(getString(R.string.message_pokemon_6))
            } else {
                showDialog(pokemonTeam)
            }
        }
    }

    private fun saveTeam(teamPokemon: MutableList<Pokemons>, pokemonTeamName: String) {
        val pokemonTeam = TeamPokemon(pokemonTeamName, teamPokemon)

        FirebaseAuth.getInstance().currentUser?.let { it1 ->
            val userDb = db.getReference("Users").child(it1.uid).child(regionName)
            val team = userDb.child("pokemonTeams").push()
            team.setValue(pokemonTeam).addOnFailureListener {
                showAlertDialog()
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Error while authenticate the pokemon trainer")
        builder.setPositiveButton("Accept", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun warningDialog(string: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Warning")
        builder.setMessage(string)
        builder.setPositiveButton("Accept", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showDialog(pokemonsTeam: MutableList<Pokemons>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.setContentView(R.layout.custom_team_dialog)

        val namePokemonEditText: EditText = dialog.findViewById(R.id.namePokemonEditText)
        val addTeam: Button = dialog.findViewById(R.id.addTeam)

        addTeam.setOnClickListener {
            saveTeam(pokemonsTeam, namePokemonEditText.text.toString())
            dialog.dismiss()
            onBackPressedFragment?.backPressed()
        }

        dialog.show()
    }

    fun setOnBackPressedListener(onBackPressedFragment: OnBackPressedFragment) {
        this.onBackPressedFragment = onBackPressedFragment
    }

}