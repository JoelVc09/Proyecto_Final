package dev.neil.proyecto_final.Nav.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R


class PagarPaseoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_pagar_paseo, container, false)
        val precio = arguments?.getDouble("precio") ?: 0.0
        val fecha = arguments?.getString("fecha") ?: ""
        val numPersonas = arguments?.getInt("numPersonas") ?: 0
        val horario = arguments?.getString("horario") ?: ""
        val paseo = arguments?.getSerializable("PASEO_KEY") as? PaseoModel
        val paseoid = paseo?.documentId
        val nombrePaseo = paseo?.nombrePaseo
        val imagenPaseo = paseo?.imagenEmpresa
        
        val btnPagarPaseo: Button = view.findViewById(R.id.btnPagarPaseo)
        btnPagarPaseo.text = "Pagar "+precio*numPersonas+" Soles"
        
        btnPagarPaseo.setOnClickListener {
            subirDatosHistorial(precio, fecha, numPersonas, horario, paseoid, nombrePaseo, imagenPaseo)
        }

        return view
    }
    private fun subirDatosHistorial(
        precio: Double,
        fecha: String,
        numPersonas: Int,
        horario: String,
        paseoid: String?,
        nombrePaseo: String?,
        imagenPaseo: String?
    ) {
        val db = FirebaseFirestore.getInstance()
        val datosHistorial = hashMapOf(
            "precio" to precio,
            "fecha" to fecha,
            "numPersonas" to numPersonas,
            "horario" to horario,
            "paseoid" to paseoid,
            "nombrePaseo" to nombrePaseo,
            "imagenPaseo" to imagenPaseo
        )

        db.collection("historial")
            .add(datosHistorial)
            .addOnSuccessListener { documentReference ->
                Log.d("PagarPaseoFragment", "Datos subidos a Firestore con ID: ${documentReference.id}")
                findNavController().navigate(R.id.action_pagarPaseoFragment_to_nav_home)
                Snackbar.make(
                    requireView(),
                    "Reserva realizada con éxito",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Log.e("PagarPaseoFragment", "Error al subir datos a Firestore", e)
                // Show error message
                Snackbar.make(
                    requireView(),
                    "Error al realizar la reserva",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }


}