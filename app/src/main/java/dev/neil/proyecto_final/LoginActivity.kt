package dev.neil.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.neil.proyecto_final.Cliente.RegistroClienteActivity
import dev.neil.proyecto_final.Empresa.RegistroEmpresaActivity
import dev.neil.proyecto_final.Nav.Nav_Drawer_Client
import dev.neil.proyecto_final.Nav.ui.home.HomeFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user : EditText = findViewById(R.id.etLoginEmail)
        val pass : EditText = findViewById(R.id.etLoginContrasenia)
        val btnIngresar : Button = findViewById(R.id.btnIngresar)
        val btnRegistrarUsuario : Button = findViewById(R.id.btnRegistrarUsuario)
        val btnRegistrarEmpresa : Button = findViewById(R.id.btnRegistrarEmpresa)

        btnIngresar.setOnClickListener {
            if(user.text.toString() == "sasa" && pass.text.toString() == "sasa"){
                val intent = Intent(this, Nav_Drawer_Client::class.java)
                startActivity(intent)
            }
        }
        btnRegistrarUsuario.setOnClickListener {
            val intent = Intent(this, RegistroClienteActivity::class.java)
            startActivity(intent)
        }
        btnRegistrarEmpresa.setOnClickListener {
            val intent = Intent(this, RegistroEmpresaActivity::class.java)
            startActivity(intent)
        }
    }
}