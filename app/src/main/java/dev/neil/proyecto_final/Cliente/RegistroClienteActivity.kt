package dev.neil.proyecto_final.Cliente

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import dev.neil.proyecto_final.model.ClienteModel

class RegistroClienteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_cliente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etNombreCliente: EditText = findViewById(R.id.etNombreCliente)
        val etEmailCliente: EditText = findViewById(R.id.etEmailCliente)
        val etConfimaEmailCliente: EditText = findViewById(R.id.etConfimaEmailCliente)
        val etContraseniaCliente: EditText = findViewById(R.id.etContraseniaCliente)
        val etConfirmeContraCliente: EditText = findViewById(R.id.etConfirmeContraCliente)
        val etNumeroCelularCliente: EditText = findViewById(R.id.etNumeroCelularCliente)
        val btnRegistroCliente: Button = findViewById(R.id.btnRegistroCliente)
        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val collectionRef = db.collection("usuarios_turismogo")

        btnRegistroCliente.setOnClickListener {
            val fullName = etNombreCliente.text.toString()
            val email = etEmailCliente.text.toString()
            val confirmaEmail = etConfimaEmailCliente.text.toString()
            val password = etContraseniaCliente.text.toString()
            val confirmaPassword = etConfirmeContraCliente.text.toString()
            val numeroCelular = etNumeroCelularCliente.text.toString()

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

                        val clienteModel = ClienteModel(email, fullName, numeroCelular, uid)
                        collectionRef.add(clienteModel)
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
                                    "Ocurrió un error al registrar el usuario: ${error.message}",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Ocurrió un error al registrar el usuario: ${task.exception?.message}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }.addOnFailureListener(this) { error ->
                    Log.e("ErrorFirebase", error.message.toString())
                }
        }

    }
}