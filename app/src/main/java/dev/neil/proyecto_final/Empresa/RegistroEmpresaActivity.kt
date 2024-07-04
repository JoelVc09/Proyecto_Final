package dev.neil.proyecto_final.Empresa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.LoginActivity
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.model.EmpresaModel

class RegistroEmpresaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_empresa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val etEmpresa: EditText = findViewById(R.id.etEmpresa)
        val etEmail: EditText = findViewById(R.id.etEmailEmpresa)
        val etConfimarEmail: EditText = findViewById(R.id.etConfirmarEmail)
        val etConstrasenia: EditText = findViewById(R.id.etContraseniaEmpresa)
        val etConfirmarContrasenia: EditText = findViewById(R.id.etConfirmarContrasenia)
        val etNumeroCelular: EditText = findViewById(R.id.etNumeroCelular)
        val etRUC: EditText = findViewById(R.id.etRUC)
        val btnRegistrarse: Button = findViewById(R.id.btnRegistrarse)

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val collectionRef = db.collection("empresas_turismogo")

        btnRegistrarse.setOnClickListener {
            val empresa = etEmpresa.text.toString()
            val email = etEmail.text.toString()
            val confirmaEmail = etConfimarEmail.text.toString()
            val password = etConstrasenia.text.toString()
            val confirmaPassword = etConfirmarContrasenia.text.toString()
            val numeroCelular = etNumeroCelular.text.toString()
            val ruc = etRUC.text.toString()

            if (email != confirmaEmail) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Los correos electrónicos deben ser iguales",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (password != confirmaPassword) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Las contraseñas deben ser iguales",
                    Snackbar.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val uid = user?.uid

                        val empresaModel = EmpresaModel(empresa, email, numeroCelular, ruc, uid)
                        collectionRef.add(empresaModel)
                            .addOnCompleteListener {
                                Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Registro exitoso",
                                    Snackbar.LENGTH_LONG
                                ).show()

                                // Redirigir a LoginActivity
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish() // Cierra la actividad actual
                            }.addOnFailureListener { error ->
                                Snackbar.make(
                                    findViewById(android.R.id.content),
                                    "Ocurrió un error al registrar la empresa: ${error.message}",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Ocurrió un error al registrar la empresa: ${task.exception?.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }.addOnFailureListener(this) { error ->
                    Log.e("ErrorFirebase", error.message.toString())
                }
        }

    }
}