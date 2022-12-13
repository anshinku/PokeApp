package com.example.pokeapp.ui.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.interfaces.interfaceadapter.ClickListener
import com.example.pokeapp.interfaces.interfacefragment.OnStartPokemonFragment
import com.example.pokeapp.model.Trainer
import com.example.pokeapp.ui.adapters.TeamPokemonAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import constants.ActivityConstants.Extra
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment : Fragment() {
    private var context = AppCompatActivity()
    private var onStartPokemonFragment: OnStartPokemonFragment? = null
    private val db = FirebaseDatabase.getInstance()
    private val list = mutableListOf<Trainer>()
    private lateinit var adapter: TeamPokemonAdapter

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
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
        regionNameTextView.text = arguments?.getString(Extra().REGION_NAME).toString()
    }

    private fun setup() {
        val dataBase = db.getReference(getString(R.string.team))

        teamView.layoutManager
        teamView.setHasFixedSize(true)
        teamView!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = TeamPokemonAdapter(object : ClickListener<Trainer> {
            override fun onClick(view: View, item: Trainer, position: Int) {
            }
        }, context, teamView)

        dataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val trainer: List<Trainer> = dataSnapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(Trainer::class.java)!!
                    }

                    adapter.updateTeamList(trainer)

                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

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