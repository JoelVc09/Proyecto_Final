package dev.neil.proyecto_final.Nav.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Nav.adapter.PaseoAdapter
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R

class HomeFragment : Fragment() {
    private lateinit var paseoAdapter: PaseoAdapter
    private var paseos: List<PaseoModel> = listOf()
    private var filteredPaseos: List<PaseoModel> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val rvGen: RecyclerView = view.findViewById(R.id.recyclerView3)

        paseoAdapter = PaseoAdapter(filteredPaseos)
        rvGen.layoutManager = LinearLayoutManager(requireContext())
        rvGen.adapter = paseoAdapter

        // Set click listener for RecyclerView items
        paseoAdapter.setOnItemClickListener { paseo ->
            val bundle = Bundle()
            bundle.putSerializable("PASEO_KEY", paseo)
            findNavController().navigate(R.id.action_nav_home_to_paseoDetailsFragment, bundle)
        }

        fetchPaseos()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filtro: EditText = view.findViewById(R.id.txtSearch)
        val menuButton: ImageButton = view.findViewById(R.id.imageFilterButton)
        val filtroActual: TextView = view.findViewById(R.id.tvRecientes)
        menuButton.setOnClickListener {
            showPopupMenu(menuButton, filtroActual)
        }

        filtro.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterPaseos(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchPaseos() {
        val db = FirebaseFirestore.getInstance()
        db.collection("paseos").addSnapshotListener { snap, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error al obtener los datos de Firestore", error)
                return@addSnapshotListener
            }
            paseos = snap!!.documents.map { document ->
                PaseoModel(
                    document.id,
                    document["imagenFondo"].toString(),
                    document["imagenEmpresa"].toString(),
                    document["nombrePaseo"].toString(),
                    document["descripcionPaseo"].toString(),
                    document["tiempo"].toString().toInt(),
                    document["rate"].toString().toFloat(),
                    document["disponibilidad"].toString(),
                    document["precios"].toString(),
                    document["contacto"].toString(),
                    emptyList(), //TODO Comentarios
                    document["ivPaseo1"].toString(),
                    document["ivPaseo2"].toString(),
                    document["ivPaseo3"].toString()
                )
            }
            filteredPaseos = paseos
            paseoAdapter.updateItems(filteredPaseos)
        }
    }

    private fun showPopupMenu(view: View, filtroActual: TextView) {
        val popupMenu = PopupMenu(requireContext(), view)
        val db = FirebaseFirestore.getInstance()

        popupMenu.inflate(R.menu.home_fragment_menu_filter) // Inflate your menu resource

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.opcion1 -> { // Reciente
                    fetchPaseos()
                    filtroActual.text = "Recientes"
                    true
                }
                R.id.opcion2 -> { // Puntuacion
                    fetchPaseosByRating()
                    filtroActual.text = "Puntuacion"
                    true
                }
                R.id.opcion3 -> { // Inicia Pronto
                    fetchPaseosByStartTime()
                    filtroActual.text = "Pronto"
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun fetchPaseosByRating() {
        val db = FirebaseFirestore.getInstance()
        db.collection("paseos").addSnapshotListener { snap, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error al obtener los datos de Firestore", error)
                return@addSnapshotListener
            }
            paseos = snap!!.documents.map { document ->
                PaseoModel(
                    document.id,
                    document["imagenFondo"].toString(),
                    document["imagenEmpresa"].toString(),
                    document["nombrePaseo"].toString(),
                    document["descripcionPaseo"].toString(),
                    document["tiempo"].toString().toInt(),
                    document["rate"].toString().toFloat(),
                    document["disponibilidad"].toString(),
                    document["precios"].toString(),
                    document["contacto"].toString(),
                    emptyList(), //TODO Comentarios
                    document["ivPaseo1"].toString(),
                    document["ivPaseo2"].toString(),
                    document["ivPaseo3"].toString()
                )
            }
            paseos = paseos.sortedByDescending { it.rate }
            filteredPaseos = paseos
            paseoAdapter.updateItems(filteredPaseos)
        }
    }

    private fun fetchPaseosByStartTime() {
        val db = FirebaseFirestore.getInstance()
        db.collection("paseos").addSnapshotListener { snap, error ->
            if (error != null) {
                Log.e("HomeFragment", "Error al obtener los datos de Firestore", error)
                return@addSnapshotListener
            }
            paseos = snap!!.documents.map { document ->
                PaseoModel(
                    document.id,
                    document["imagenFondo"].toString(),
                    document["imagenEmpresa"].toString(),
                    document["nombrePaseo"].toString(),
                    document["descripcionPaseo"].toString(),
                    document["tiempo"].toString().toInt(),
                    document["rate"].toString().toFloat(),
                    document["disponibilidad"].toString(),
                    document["precios"].toString(),
                    document["contacto"].toString(),
                    emptyList(), //TODO Comentarios
                    document["ivPaseo1"].toString(),
                    document["ivPaseo2"].toString(),
                    document["ivPaseo3"].toString()
                )
            }
            paseos = paseos.sortedBy { it.tiempo }
            filteredPaseos = paseos
            paseoAdapter.updateItems(filteredPaseos)
        }
    }

    private fun filterPaseos(query: String) {
        filteredPaseos = if (query.isEmpty()) {
            paseos
        } else {
            paseos.filter {
                it.nombrePaseo.contains(query, ignoreCase = true) ||
                        it.descripcionPaseo.contains(query, ignoreCase = true)
            }
        }
        paseoAdapter.updateItems(filteredPaseos)
    }
}
