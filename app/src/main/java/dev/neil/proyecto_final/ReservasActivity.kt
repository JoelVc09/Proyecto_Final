package dev.neil.proyecto_final

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Nav.adapter.HistorialClienteAdapter
import dev.neil.proyecto_final.model.HistorialClienteModel

class ReservasActivity : AppCompatActivity() {
    private lateinit var historialAdapter: HistorialClienteAdapter
    private var historialList: List<HistorialClienteModel> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reservas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rvHistorialCliente: RecyclerView = findViewById(R.id.rvHistorialCliente)
        historialAdapter = HistorialClienteAdapter(historialList)
        rvHistorialCliente.layoutManager = LinearLayoutManager(this)
        rvHistorialCliente.adapter = historialAdapter

        fetchHistorial()
        val ibReturn: ImageButton = findViewById(R.id.ibReturn)
        ibReturn.setOnClickListener {
            finish()
        }
    }

    private fun fetchHistorial() {
        val db = FirebaseFirestore.getInstance()

        db.collection("historial").get()
            .addOnSuccessListener { result ->
                val historialItems = result.map { document ->
                    HistorialClienteModel(
                        document.getString("nombrePaseo") ?: "",
                        document.getString("fecha") ?: "",
                        document.getString("imagenPaseo") ?: ""
                    )
                }
                historialAdapter.updateItems(historialItems)
            }
            .addOnFailureListener { exception ->
                Log.e("ReservasActivity", "Error al obtener los datos de Firestore", exception)
            }
    }
}
