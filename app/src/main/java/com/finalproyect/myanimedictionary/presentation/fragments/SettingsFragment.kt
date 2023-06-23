package com.finalproyect.myanimedictionary.presentation.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.facebook.login.LoginManager
import com.finalproyect.myanimedictionary.R
import com.finalproyect.myanimedictionary.databinding.FragmentSettingsBinding
import com.finalproyect.myanimedictionary.presentation.activities.AuthActivity
import com.finalproyect.myanimedictionary.presentation.activities.ProviderType
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bundle = activity?.intent?.extras
        setup(bundle?.getString("email") ?: "", bundle?.getString("provider") ?: "")
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater)
    }

    private fun setup(email: String, provider: String) {

        binding.email.text = email

        binding.loginType.text = "Register with: $provider"

        if (provider == ProviderType.GOOGLE.name) {
            Picasso.get().load(FirebaseAuth.getInstance().currentUser?.photoUrl.toString()).into(binding.profilePicture)
        } else {
            Picasso.get().load(R.drawable.imagen_perfil).into(binding.profilePicture)
        }

        binding.logout.setOnClickListener {

            val prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
            prefs?.clear()
            prefs?.apply()

            if (provider == ProviderType.FACEBOOK.name) {
                LoginManager.getInstance().logOut()
            }

            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, AuthActivity::class.java)
            startActivity(intent)
        }
    }
}
