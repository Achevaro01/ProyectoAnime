package com.finalproyect.myanimedictionary.presentation.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.finalproyect.myanimedictionary.R
import com.finalproyect.myanimedictionary.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    private val callbackManager = CallbackManager.Factory.create()

    private val GOOGLE_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Integración de Firebase completa")
        analytics.logEvent("InitScreen", bundle)

        // Setup
        setup()
        session()
    }

    override fun onStart() {
        super.onStart()

        binding.authLayout.visibility = View.VISIBLE
    }

    private fun session() {

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            binding.authLayout.visibility = View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }
    }

    private fun setup() {

        binding.signUpButton.setOnClickListener {
            if (binding.emailEditText.text?.isNotEmpty()!! && binding.passwordEditText.text?.isNotEmpty()!!) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        Toast.makeText(this, "There's must be a problem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            if (binding.emailEditText.text?.isNotEmpty()!! && binding.passwordEditText.text?.isNotEmpty()!!) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        Toast.makeText(this, "There's must be a problem", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.googleButton.setOnClickListener {

            // Configuración

            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        binding.facebookButton.setOnClickListener {

            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

            LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onSuccess(result: LoginResult?) {

                    result?.let {

                        val token = it.accessToken

                        val credential = FacebookAuthProvider.getCredential(token.token)
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(it.result?.user?.email ?: "", ProviderType.FACEBOOK)
                            } else {
                                showAlert()
                            }
                        }
                    }
                }

                override fun onCancel() {
                    Toast.makeText(this@AuthActivity, "Operation canceled", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException?) {
                    showAlert()
                }
            })
        }
    }

        private fun showHome(email: String, provider: ProviderType) {

            val mainIntent = Intent(this, MainActivity::class.java).apply {
                putExtra("email", email)
                putExtra("provider", provider.toString())
            }
            binding.emailEditText.text?.clear()
            binding.passwordEditText.text?.clear()
            startActivity(mainIntent)
            finish()
        }


        private fun showAlert() {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Se ha producido un error autenticando al usuario")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(account.email ?: "", ProviderType.GOOGLE)
                    } else {
                        showAlert()
                    }
                }
            } catch (e: ApiException) {
                showAlert()
            }
        }
    }
}