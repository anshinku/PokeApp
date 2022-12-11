package com.example.pokeapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokeapp.R
import com.example.pokeapp.model.RegionsResult
import com.example.pokeapp.ui.adapters.RegionsAdapter
import com.example.pokeapp.ui.interfacebuttons.ClickListener
import com.example.pokeapp.viewmodel.InfoRegionsViewModel
import com.example.pokeapp.viewmodel.RegionsListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import constants.ActivityConstants.Extra
import constants.ActivityConstants.Pref
import kotlinx.android.synthetic.main.activity_home.*

private var provider: String? = null
private var email: String? = null

private lateinit var regionsViewModel: RegionsListViewModel
private lateinit var regionInfoViewModel: InfoRegionsViewModel

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initialiseVariables()
        setup()

    }

    override fun onResume() {
        super.onResume()
        setListeners()
    }

    override fun onPause() {
        super.onPause()
        unSetListeners()
    }

    private fun setListeners() {
        profileMenu.setOnClickListener { showPopup(it) }

    }

    private fun unSetListeners() {
        profileMenu.setOnClickListener(null)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        return
    }

    private fun initialiseVariables() {
        regionsViewModel = ViewModelProvider(this)[RegionsListViewModel::class.java]
        regionInfoViewModel = ViewModelProvider(this)[InfoRegionsViewModel::class.java]

        val bundle = intent.extras
        email = bundle?.getString(Extra().email)
        provider = bundle?.getString(Extra().providerType)

        emailId.text = email

        //save data
        val prefs = getSharedPreferences(Pref().preferenceFileKey, Context.MODE_PRIVATE).edit()
        prefs.putString(Extra().email, email)
        prefs.putString(Extra().providerType, provider)
        prefs.apply()
    }

    private fun setup() {
        regionsView!!.setHasFixedSize(true)
        regionsView!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        regionsView.adapter = RegionsAdapter(object : ClickListener<RegionsResult> {
            override fun onClick(view: View, item: RegionsResult, position: Int) {
                getPokedexes(item.name)

            }
        }, this, regionsView)

        regionsViewModel.getRegionList()

        regionsViewModel.regionsList.observe(this) { list ->
            (regionsView.adapter as RegionsAdapter).updateRegionList(list)
            (regionsView.adapter as RegionsAdapter).setRegionIconList(
                resources.getStringArray(R.array.region_icons).asList()
            )
        }

    }

    fun getPokedexes(name: String) {
        regionInfoViewModel.getRegion(name)
        regionInfoViewModel.regionInfo.observe(this) { pokedexes ->
            Toast.makeText(this, pokedexes.name, Toast.LENGTH_SHORT).show()

            val intent = Intent(this@HomeActivity, TeamActivity::class.java)
            intent.putExtra(Extra().regionName, name)
            intent.putExtra(Extra().pokedexesName, pokedexes.name)
            startActivity(intent)

        }
    }


    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.trainer_menu)

        popup.setOnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.logOut -> {

                    val prefs = getSharedPreferences(
                        Pref().preferenceFileKey, Context.MODE_PRIVATE
                    ).edit()
                    prefs.clear()
                    prefs.apply()

                    FirebaseAuth.getInstance().signOut()
                    onBackPressedDispatcher.onBackPressed()
                }

                R.id.testCrashlytics -> {
                    FirebaseCrashlytics.getInstance().setUserId(email!!)
                    FirebaseCrashlytics.getInstance().setCustomKey("provider", provider!!)

                    FirebaseCrashlytics.getInstance().log(getString(R.string.test_crashlyticss))

                    throw java.lang.RuntimeException("Force error")
                }
            }

            true
        }

        popup.show()
    }
}