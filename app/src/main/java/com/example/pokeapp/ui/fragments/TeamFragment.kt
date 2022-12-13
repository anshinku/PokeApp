package com.example.pokeapp.ui.fragments

import android.content.Context
import android.os.Bundle
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
import com.example.pokeapp.model.TeamPokemon
import com.example.pokeapp.ui.adapters.TeamPokemonAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import constants.ActivityConstants.Extra
import kotlinx.android.synthetic.main.fragment_team.*


class TeamFragment : Fragment() {
    private var context = AppCompatActivity()
    private var onStartPokemonFragment: OnStartPokemonFragment? = null
    private val db = FirebaseDatabase.getInstance()
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

    val teamList = mutableListOf<TeamPokemon>()

    private fun setup() {
        teamView.setHasFixedSize(true)
        teamView!!.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = TeamPokemonAdapter(object : ClickListener<TeamPokemon> {
            override fun onClick(view: View, item: TeamPokemon, position: Int) {
            }
        }, context, teamView)

        FirebaseAuth.getInstance().currentUser?.let { it1 ->
            val userDb = db.getReference("Users").child(it1.uid).child("pokemonTeams");
            userDb.addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val team = snapshot.getValue(TeamPokemon::class.java)

                    if (team != null) teamList.add(team);

                    adapter.updateTeamList(teamList);
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        teamView.adapter = adapter

    }

    fun setOnStartPokemonFragmentListener(onStartPokemonFragment: OnStartPokemonFragment) {
        this.onStartPokemonFragment = onStartPokemonFragment
    }

    private fun setOnclickListeners() {
        createTeam.setOnClickListener {
            onStartPokemonFragment?.startPokemonFragment()
            onStart()
        }
    }


}