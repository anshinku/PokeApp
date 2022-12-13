package com.example.pokeapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pokeapp.R
import com.example.pokeapp.manager.ProviderType
import com.example.pokeapp.model.Users
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import constants.ActivityConstants.Extra
import constants.ActivityConstants.Pref
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity() {
    private val db = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setup()
        session()
    }

    override fun onResume() {
        super.onResume()
        setListeners()
    }

    private fun setListeners() {
        registerNowButton.setOnClickListener {
            goToSinInOrSinUp(true)
        }

        bakLogin.setOnClickListener {
            goToSinInOrSinUp(false)
        }

        singUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    emailEditText.text.toString(), passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val email = it.result.user?.email.toString()
                        val provider = ProviderType.BASIC
                        showHome(email, provider)
                        saveUser(email, provider)
                    } else {
                        showAlertDialog()
                    }
                }
            }
        }

        singInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(), passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlertDialog()
                    }
                }
            }
        }

        googleButton.setOnClickListener {
            //Configuration
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            activityResultLauncher.launch(googleClient.signInIntent)
        }
    }

    override fun onPause() {
        super.onPause()
        unSetListeners()
    }

    private fun unSetListeners() {
        bakLogin.setOnClickListener(null)
        singUpButton.setOnClickListener(null)
        singInButton.setOnClickListener(null)
        googleButton.setOnClickListener(null)
        registerNowButton.setOnClickListener(null)
    }

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResultRegistry ->

            if (activityResultRegistry.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(activityResultRegistry.data)

                try {
                    val account = task.getResult(ApiException::class.java)

                    if (account != null) {
                        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credentials)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val email = account.email ?: ""
                                    val provider = ProviderType.GOOGLE
                                    saveUser(email, provider)
                                    showHome(email, provider)
                                } else {
                                    showAlertDialog()
                                }
                            }
                    }
                } catch (e: ApiException) {
                    showAlertDialog()
                }
            }
        }

    override fun onStart() {
        super.onStart()

        authContainer.visibility = View.VISIBLE
    }

    private fun setup() {

        if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
            singInButton.isEnabled = true
        } else {
            singInButton.isEnabled = true
        }

    }

    private fun session() {
        val prefs = getSharedPreferences(Pref().preferenceFileKey, Context.MODE_PRIVATE)
        val email = prefs.getString(Extra().email, null)
        val provider = prefs.getString(Extra().providerType, null)

        if (email != null && provider != null) {
            authContainer.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

    }

    private fun goToSinInOrSinUp(isSinUp: Boolean) {
        if (isSinUp) {
            continue_with_container.visibility = View.GONE
            singInButton.visibility = View.GONE
            recover_password.visibility = View.GONE
            singUp_container.visibility = View.GONE
            googleButton.visibility = View.GONE

            singUpButton.visibility = View.VISIBLE
            backLogin_container.visibility = View.VISIBLE
        } else {
            continue_with_container.visibility = View.VISIBLE
            singInButton.visibility = View.VISIBLE
            recover_password.visibility = View.VISIBLE
            singUp_container.visibility = View.VISIBLE
            googleButton.visibility = View.VISIBLE

            singUpButton.visibility = View.GONE
            backLogin_container.visibility = View.GONE
        }

    }

    private fun saveUser(email: String, provider: ProviderType) {
        val userDB = db.getReference("Users")
        val user = Users(email, provider.name)
        FirebaseAuth.getInstance().currentUser?.let { it1 ->
            userDB.child(it1.uid).setValue(user).addOnFailureListener {
                showAlertDialog()
            }
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Error while authenticate the pokemon trainer")
        builder.setPositiveButton("Accept", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra(Extra().email, email)
            putExtra(Extra().providerType, provider.name)
        }
        startActivity(homeIntent)
    }

}