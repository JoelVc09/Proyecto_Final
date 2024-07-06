package dev.neil.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Cliente.RegistroClienteActivity
import dev.neil.proyecto_final.Empresa.EmpresaHistorialActividadesFragment
import dev.neil.proyecto_final.Empresa.RegistroEmpresaActivity
import dev.neil.proyecto_final.Nav.Nav_Drawer_Client


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etLoginEmail: EditText = findViewById(R.id.etLoginEmail)
        val etLoginContrasenia: EditText = findViewById(R.id.etLoginContrasenia)
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)
        val btnRegistrarEmpresa: Button = findViewById(R.id.btnRegistrarEmpresa)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        btnRegistrarUsuario.setOnClickListener {
            startActivity(
                Intent(this, RegistroClienteActivity::class.java)
            )
        }

        btnRegistrarEmpresa.setOnClickListener {
            startActivity(
                Intent(this, RegistroEmpresaActivity::class.java)
            )
        }

        btnIngresar.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginContrasenia.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        checkUserType(email)
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Credenciales inválidas",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }

    private fun checkUserType(email: String) {
        var TipoUsuario: String? = null
        db.collection("usuarios_turismogo")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Es un usuario
                    val intent = Intent(this, Nav_Drawer_Client::class.java)
                    startActivity(intent)

                } else {
                    // Verificar si es una empresa
                    db.collection("empresas_turismogo")
                        .whereEqualTo("email", email)
                        .get()
                        .addOnSuccessListener { empresaDocs ->
                            if (!empresaDocs.isEmpty) {
                                // Es una empresa
                                val intent = Intent(this, Nav_Drawer_Client::class.java)
                                startActivity(intent)
                            } else {
                                Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Usuario no encontrado",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            }
            .addOnFailureListener {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error al verificar el usuario",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }
}