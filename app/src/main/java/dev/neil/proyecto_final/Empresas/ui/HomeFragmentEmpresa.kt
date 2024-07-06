package dev.neil.proyecto_final.Empresas.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Cliente.RegistroClienteActivity
import dev.neil.proyecto_final.Empresas.Adapter.PaseoEmpresaAdapter
import dev.neil.proyecto_final.Empresas.model.PaseoEmpresaModel
import dev.neil.proyecto_final.Nav.adapter.PaseoAdapter
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.databinding.FragmentHomeEmpresaBinding

class HomeFragmentEmpresa : Fragment() {
    private lateinit var paseoempresaAdapter: PaseoEmpresaAdapter
    private var paseos: List<PaseoEmpresaModel> = listOf()
    private var filteredPaseos: List<PaseoEmpresaModel> = listOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_empresa, container, false)
        val rv_paseo_empresa: RecyclerView = view.findViewById(R.id.rv_paseo_empresa)
        val btnCrearActEmpre : Button = view.findViewById(R.id.btnCrearActEmpre)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        btnCrearActEmpre.setOnClickListener {
            val intent = Intent(requireContext(), DescripcionEmpresa::class.java)
            startActivity(intent)
        }
/*
        paseoempresaAdapter = PaseoEmpresaAdapter(filteredPaseos)
        rv_paseo_empresa.layoutManager = LinearLayoutManager(requireContext())
        rv_paseo_empresa.adapter = paseoempresaAdapter

        paseoempresaAdapter.setOnItemClickListener { paseo ->
            val bundle = Bundle()
            bundle.putSerializable("PASEO_KEY", paseo)
            findNavController().navigate(R.id.action_nav_home_to_paseoDetailsFragment, bundle)
        }*/

        return view
    }
/*
    private fun filterPaseos(query: String) {
        filteredPaseos = if (query.isEmpty()) {
            paseos
        } else {
            paseos.filter {
                it.nombrePaseo.contains(query, ignoreCase = true) ||
                        it.descripcionPaseo.contains(query, ignoreCase = true)
            }
        }
        paseoempresaAdapter.updateItems(filteredPaseos)
    }*/
}