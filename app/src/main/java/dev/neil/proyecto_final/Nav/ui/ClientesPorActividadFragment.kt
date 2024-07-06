package dev.neil.proyecto_final.Nav.ui.empresa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Nav.adapter.ClienteActividadAdapter
import dev.neil.proyecto_final.Nav.model.ClienteActividadModel
import dev.neil.proyecto_final.R

class ClientesPorActividadFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClienteActividadAdapter
    private lateinit var db: FirebaseFirestore
    private var empresaId: String? = null
    private var empresaUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            empresaId = it.getString("empresaId")
            empresaUid = it.getString("empresaUid")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clientes_por_actividad, container, false)

        recyclerView = view.findViewById(R.id.rvClientesActividad)
        db = FirebaseFirestore.getInstance()

        adapter = ClienteActividadAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        if (empresaId != null && empresaUid != null) {
            fetchPaseosYClientes(empresaId!!, empresaUid!!)
        } else {
            Toast.makeText(context, "Error: Información de empresa no disponible", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun fetchPaseosYClientes(empresaId: String, empresaUid: String) {
        db.collection("paseos")
            .whereEqualTo("uid", empresaUid)
            .get()
            .addOnSuccessListener { paseosDocuments ->
                if (paseosDocuments.isEmpty) {
                    fetchPaseosPorEmpresaId(empresaId)
                } else {
                    val paseoIds = paseosDocuments.documents.map { it.id }
                    fetchClientesPorPaseos(paseoIds)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al cargar las actividades", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchPaseosPorEmpresaId(empresaId: String) {
        db.collection("paseos")
            .whereEqualTo("empresaId", empresaId)
            .get()
            .addOnSuccessListener { paseosDocuments ->
                val paseoIds = paseosDocuments.documents.map { it.id }
                if (paseoIds.isNotEmpty()) {
                    fetchClientesPorPaseos(paseoIds)
                } else {
                    Toast.makeText(context, "No tienes actividades registradas", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al cargar las actividades", Toast.LENGTH_SHORT).show()
            }
    }

    private fun fetchClientesPorPaseos(paseoIds: List<String>) {
        db.collection("historial")
            .whereIn("paseoid", paseoIds)
            .get()
            .addOnSuccessListener { historiales ->
                val clientes = mutableListOf<ClienteActividadModel>()
                for (historial in historiales) {
                    val clienteUid = historial.getString("uid") ?: continue
                    db.collection("usuarios_turismogo")
                        .document(clienteUid)
                        .get()
                        .addOnSuccessListener { userDoc ->
                            val cliente = ClienteActividadModel(
                                uid = clienteUid,
                                fullName = userDoc.getString("fullName") ?: "",
                                email = userDoc.getString("email") ?: "",
                                numeroCelular = userDoc.getString("numeroCelular") ?: "",
                                fecha = historial.getString("fecha") ?: "",
                                horario = historial.getString("horario") ?: "",
                                numPersonas = historial.getLong("numPersonas")?.toInt() ?: 0,
                                precio = historial.getDouble("precio") ?: 0.0,
                                nombrePaseo = historial.getString("nombrePaseo") ?: ""
                            )
                            clientes.add(cliente)
                            adapter.updateClientes(clientes)
                        }
                }
                if (clientes.isEmpty()) {
                    Toast.makeText(context, "No hay clientes registrados para tus actividades", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al cargar los clientes", Toast.LENGTH_SHORT).show()
            }
    }
}