package dev.neil.proyecto_final.Empresa

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.neil.proyecto_final.R

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
        btnRegistrarse.setOnClickListener {
            if (etEmail.text==etConfimarEmail.text && etConstrasenia.text==etConfirmarContrasenia){
                TODO("Enviar registrar los datos en la BD FireBase")
            }else{
                Toast.makeText(this,"Contraseña o correos incorrectos.",Toast.LENGTH_LONG).show()
            }

        }
    }
}