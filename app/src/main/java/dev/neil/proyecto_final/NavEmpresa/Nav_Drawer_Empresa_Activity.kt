package dev.neil.proyecto_final.NavEmpresa

import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.databinding.ActivityNavDrawerEmpresaBinding

class Nav_Drawer_Empresa_Activity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavDrawerEmpresaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavDrawerEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavDrawerEmpresa.toolbarEmpresa)

        binding.appBarNavDrawerEmpresa.fabEmpresa.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab_empresa).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayoutEmpresa
        val navView: NavigationView = binding.navViewEmpresa
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer_empresa)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav__drawer__empresa_, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer_empresa)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}