package dev.neil.proyecto_final.Empresas

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.databinding.ActivityMainEmpresasBinding

class Nav_Drawer_Empresas : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainEmpresasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainEmpresasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home_Empresas,
                R.id.nav_agregar_actividad,
                R.id.clientesPorActividadFragment
            ),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_clientes_por_actividad -> {
                    val auth = FirebaseAuth.getInstance()
                    val db = FirebaseFirestore.getInstance()

                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        Log.d("Empresa", "UID del usuario actual: ${currentUser.uid}")
                        db.collection("empresas_turismogo")
                            .whereEqualTo("uid", currentUser.uid)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                if (!querySnapshot.isEmpty) {
                                    val document = querySnapshot.documents[0]
                                    val empresaId = document.id
                                    val empresaUid = document.getString("uid")

                                    // Ahora tienes tanto el ID del documento como el UID almacenado
                                    Log.d("Empresa", "Documento encontrado. ID: $empresaId, UID: $empresaUid")

                                    // Aquí puedes proceder con la navegación o lo que necesites hacer
                                    val bundle = Bundle().apply {
                                        putString("empresaId", empresaId)
                                        putString("empresaUid", empresaUid)
                                    }
                                    navController.navigate(R.id.clientesPorActividadFragment, bundle)
                                } else {
                                    Log.e("Empresa", "Documento no encontrado para UID: ${currentUser.uid}")
                                    Toast.makeText(this, "Error: Documento de empresa no encontrado", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e("Empresa", "Error al buscar documento: ${e.message}")
                                Toast.makeText(this, "Error al obtener datos de la empresa", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Log.e("Empresa", "Usuario no autenticado")
                        Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
                    }
                    drawerLayout.closeDrawers()
                    true
                }
                else -> {
                    val handled = NavigationUI.onNavDestinationSelected(menuItem, navController)
                    if (handled) {
                        drawerLayout.closeDrawers()
                    }
                    handled
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_empresas, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}