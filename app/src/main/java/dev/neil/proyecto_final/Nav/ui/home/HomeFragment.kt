package dev.neil.proyecto_final.Nav.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        paseos = playList()
        filteredPaseos = paseos
        paseoAdapter = PaseoAdapter(filteredPaseos)

        rvGen.layoutManager = LinearLayoutManager(requireContext())
        rvGen.adapter = paseoAdapter

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

    private fun showPopupMenu(view: View, filtroActual: TextView) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.home_fragment_menu_filter) // Inflate your menu resource

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.opcion1 -> { // Reciente
                    paseos = playList()
                    filterPaseos("")
                    filtroActual.text = "Recientes"
                    true
                }
                R.id.opcion2 -> { // Puntuacion
                    paseos = paseos.sortedByDescending { it.rate }
                    filterPaseos("")
                    filtroActual.text = "Puntuacion"
                    true
                }
                R.id.opcion3 -> { // Inicia Pronto
                    paseos = paseos.sortedBy { it.tiempo }
                    filterPaseos("")
                    filtroActual.text = "Pronto"
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun filterPaseos(query: String) {
        filteredPaseos = if (query.isEmpty()) {
            paseos
        } else {
            paseos.filter {
                it.nombrePaseo.contains(query, ignoreCase = true) ||
                        it.desripcionPaseo.contains(query, ignoreCase = true)
            }
        }
        paseoAdapter.updateItems(filteredPaseos)
    }

    private fun playList(): List<PaseoModel> {
        val lstSong: ArrayList<PaseoModel> = ArrayList()

        lstSong.add(
            PaseoModel(
                R.drawable.img1,
                R.drawable.img1,
                "Habitación en Zornitsa, Bulgaria",
                "2 camas. Baño privado en el alojamiento",
                2,
                4.5f
            )
        )
        lstSong.add(
            PaseoModel(R.drawable.img2,
                R.drawable.img2,
                "Vivienda rentada entero en Brașov, Rumanía",
                "6 huéspedes, 2 habitaciones, 2 camas, 2 baños",
                24,
                2.5f
            )
        )
        lstSong.add(
            PaseoModel(R.drawable.img3,
                R.drawable.img3,
                "Villa entero en Agios Georgios, Grecia",
                "4 huéspedes, 2 habitaciones, 2 camas, 2 baños",
                22,
                3.4f
            )
        )
        lstSong.add(
            PaseoModel(R.drawable.img4,
                R.drawable.img4,
                "Minicasa en Varlaam, Rumanía",
                "2 camas. Baño privado en el alojamiento",
                12,
                4.5f
            )
        )
        return lstSong
    }
}
