package app.netlify.accessdeniedgc.classko.ui.`class`

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import app.netlify.accessdeniedgc.classko.R
import app.netlify.accessdeniedgc.classko.databinding.ActivityClassBinding

class ClassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.class_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        setupActionBarWithNavController(navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}