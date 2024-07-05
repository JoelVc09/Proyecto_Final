package dev.neil.proyecto_final

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

class Contactanos : AppCompatActivity() {
    private lateinit var etAsunto: EditText
    private lateinit var etMensaje: EditText
    private lateinit var btnEnviarMsj: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contactanos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etAsunto = findViewById(R.id.etAsunto)
        etMensaje = findViewById(R.id.etMensaje)
        btnEnviarMsj = findViewById(R.id.btnEnviarMsj)

        btnEnviarMsj.setOnClickListener {
            enviarMensaje()
        }
    }

    private fun guardarMensaje(mensajeMap: Map<String, Any>) {
        db.collection("mensajes_contacto")
            .add(mensajeMap)
            .addOnSuccessListener {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Mensaje enviado con éxito",
                    Snackbar.LENGTH_LONG
                ).show()
                etAsunto.text.clear()
                etMensaje.text.clear()
                // Regresar al HomeFragment
                volverAlHome()
            }
            .addOnFailureListener { e ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Error al enviar el mensaje: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    private fun enviarMensaje() {
        val asunto = etAsunto.text.toString().trim()
        val mensaje = etMensaje.text.toString().trim()

        if (asunto.isEmpty() || mensaje.isEmpty()) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Por favor, complete todos los campos",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val currentUser = auth.currentUser
        val mensajeMap = mutableMapOf(
            "asunto" to asunto,
            "mensaje" to mensaje,
            "timestamp" to System.currentTimeMillis()
        )

        if (currentUser != null) {
            db.collection("usuarios_turismogo").whereEqualTo("uid", currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val userDoc = documents.documents[0]
                        mensajeMap["nombre"] = userDoc.getString("nombre") ?: ""
                        mensajeMap["email"] = userDoc.getString("email") ?: ""
                        mensajeMap["numeroCelular"] = userDoc.getString("numeroCelular") ?: ""
                        mensajeMap["uid"] = currentUser.uid
                    }
                    guardarMensaje(mensajeMap)
                }
                .addOnFailureListener { e ->
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Error al obtener información del usuario: ${e.message}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
        } else {
            guardarMensaje(mensajeMap)
        }
    }

    private fun volverAlHome() {
        // Asumiendo que estás usando un NavController
        // Si no, ajusta esto según tu navegación
        finish() // Esto cerrará la actividad actual y volverá a la anterior
    }
}