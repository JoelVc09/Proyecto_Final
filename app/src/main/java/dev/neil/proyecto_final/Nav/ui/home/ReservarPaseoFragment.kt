package dev.neil.proyecto_final.Nav.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R

class ReservarPaseoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_reservar_paseo, container, false)
        val tvNombrePaseo: TextView = view.findViewById(R.id.tvNombrePaseo)
        val tvNombreUsuario: TextView = view.findViewById(R.id.tvNombreUsuario)
        val ivEmpresa: ImageView = view.findViewById(R.id.ivEmpresa)
        val spHorario: Spinner = view.findViewById(R.id.spHorario)
        val spNumMax: Spinner = view.findViewById(R.id.spNumMax)
        val btnIrAPagar: Button = view.findViewById(R.id.btnIrAPagar)
        val tvSubTotal: TextView = view.findViewById(R.id.tvSubTotal)
        val db = FirebaseFirestore.getInstance()
        val paseo = arguments?.getSerializable("PASEO_KEY") as? PaseoModel
        val auth = FirebaseAuth.getInstance()
        tvNombrePaseo.text = paseo?.nombrePaseo

        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("usuarios_turismogo").whereEqualTo("uid", currentUser.uid).get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val userDoc = documents.documents[0]
                        val fullName = userDoc.getString("fullName") ?: "" // Extract fullName here
                        tvNombreUsuario.text = fullName
                    }
                }

        }

        Picasso.get().load(paseo?.imagenEmpresa).into(ivEmpresa)

        val numMax = paseo?.grupoMax ?: 1
        val primerTurno = paseo?.primerTurno ?: 0
        val intervaloTiempo = paseo?.intervaloTiempo ?: 1
        val precio = paseo?.precios?.substring(3)?.toDoubleOrNull() ?: 0.0

        // Initialize spNumMax Spinner
        val numMaxList = (1..numMax).toList()
        val numMaxAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numMaxList)
        numMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNumMax.adapter = numMaxAdapter

        // Initialize spHorario Spinner
        val horarioList = generateHorarioList(primerTurno, intervaloTiempo)
        val horarioAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, horarioList)
        horarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spHorario.adapter = horarioAdapter

        // Update subtotal when the number of users changes
        spNumMax.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedNum = numMaxList[position]
                val subtotal = selectedNum * precio
                tvSubTotal.text = getString(R.string.subtotal_format, subtotal)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }

        return view
    }

    private fun generateHorarioList(primerTurno: Int, intervaloTiempo: Int): List<String> {
        val horarioList = mutableListOf<String>()
        var currentTurno = primerTurno

        while (currentTurno < 22) { // 22 hours is 10 PM
            val hour = String.format("%02d:00", currentTurno)
            horarioList.add(hour)
            currentTurno += intervaloTiempo
        }

        return horarioList
    }
}
