package dev.neil.proyecto_final.Nav.ui.home

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R
import java.util.Date
import java.util.Locale

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

        val numMax = paseo?.grupoMax ?:""
        var numMaxInt = numMax.toInt()
        val primerTurno = paseo?.primerTurno ?: 0
        val intervaloTiempo = paseo?.intervaloTiempo ?: 1
        val precio = paseo?.precios?.substring(3)?.toDoubleOrNull() ?: 0.0


        val numMaxList = (1..numMaxInt).toList()
        val numMaxAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, numMaxList)
        numMaxAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNumMax.adapter = numMaxAdapter


        val horarioList = generateHorarioList(primerTurno, intervaloTiempo)
        val horarioAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, horarioList)
        horarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spHorario.adapter = horarioAdapter


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
        btnIrAPagar.setOnClickListener {
            val selectedNumPeople = spNumMax.selectedItem.toString().toInt()
            val selectedTime = spHorario.selectedItem.toString()

            val bundle = Bundle()
            bundle.putSerializable("PASEO_KEY", paseo)
            bundle.putDouble("precio", precio)
            bundle.putString("fecha", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                Date()
            ))
            bundle.putInt("numPersonas", selectedNumPeople)
            bundle.putString("horario", selectedTime)

            findNavController().navigate(R.id.action_reservarPaseoFragment_to_pagarPaseoFragment, bundle)
        }
        return view
    }

    private fun generateHorarioList(primerTurno: Int, intervaloTiempo: Int): List<String> {
        val horarioList = mutableListOf<String>()
        var currentTurno = primerTurno
        var i=0
        while (currentTurno < 22) { // 22 hours is 10 PM
            val hour = String.format("%02d:00", currentTurno)
            horarioList.add(hour)
            currentTurno += intervaloTiempo
            i=i+1
            if (i>15){
                break
            }
        }

        return horarioList
    }
}
