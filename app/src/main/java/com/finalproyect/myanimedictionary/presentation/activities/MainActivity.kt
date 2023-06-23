package com.finalproyect.myanimedictionary.presentation.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.finalproyect.myanimedictionary.R
import com.finalproyect.myanimedictionary.databinding.ActivityMainBinding
import com.finalproyect.myanimedictionary.presentation.fragments.FavouritesFragment
import com.finalproyect.myanimedictionary.presentation.fragments.HomeFragment
import com.finalproyect.myanimedictionary.presentation.fragments.SettingsFragment

enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}
class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var context: Context
    }

    private lateinit var binding:ActivityMainBinding

    private var contador = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        // Guardado de datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        val homeFragment = HomeFragment()
        val favouritesFragment = FavouritesFragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(homeFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> makeCurrentFragment(homeFragment)
                R.id.favouritesFragment -> makeCurrentFragment(favouritesFragment)
                R.id.settingsFragment -> makeCurrentFragment(settingsFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment : Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.frame_container, fragment)
        commit()
    }

    override fun onBackPressed() {
        if(binding.bottomNavigation.selectedItemId == R.id.homeFragment){

            if (contador == 0) {
                finish()
            } else {
                super.onBackPressed()
            }
        } else {
            binding.bottomNavigation.selectedItemId = R.id.homeFragment
        }
    }
}