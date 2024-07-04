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
import dev.neil.proyecto_final.Cliente.RegistroClienteActivity
import dev.neil.proyecto_final.Empresa.EmpresaHistorialActividadesFragment
import dev.neil.proyecto_final.Empresa.RegistroEmpresaActivity
import dev.neil.proyecto_final.Nav.Nav_Drawer_Client

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

        val etLoginEmail: EditText = findViewById(R.id.etLoginEmail)
        val etLoginContrasenia: EditText = findViewById(R.id.etLoginContrasenia)
        val btnIngresar: Button = findViewById(R.id.btnIngresar)
        val btnRegistrarUsuario: Button = findViewById(R.id.btnRegistrarUsuario)
        val btnRegistrarEmpresa: Button = findViewById(R.id.btnRegistrarEmpresa)
        val auth = FirebaseAuth.getInstance()

        btnRegistrarUsuario.setOnClickListener {
            startActivity(
                Intent(this
                , RegistroClienteActivity::class.java)
            )
        }

        btnRegistrarEmpresa.setOnClickListener {
            startActivity(
                Intent(this
                    , RegistroEmpresaActivity::class.java)
            )
        }

        btnIngresar.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginContrasenia.text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Inicio de sesión exitoso"
                            , Snackbar.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(this
                            , Nav_Drawer_Client::class.java)
                        )
                    }else{
                        Snackbar.make(
                            findViewById(android.R.id.content)
                            , "Credenciales inválidas"
                            , Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
        }

    }
}