package dev.neil.proyecto_final.Nav.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.neil.proyecto_final.Nav.adapter.PaseoAdapter
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View =  inflater.inflate(R.layout.fragment_home, container, false)
        val rvGen: RecyclerView = view.findViewById(R.id.recyclerView3)

        rvGen.layoutManager = LinearLayoutManager(requireContext())
        rvGen.adapter = PaseoAdapter(playList())

        return view


    }

    private fun playList(): List<PaseoModel>{
        var lstSong: ArrayList<PaseoModel> = ArrayList()

        lstSong.add(
            PaseoModel(R.drawable.img1
                ,R.drawable.img1
                ,"Habitación en Zornitsa, Bulgaria"
                ,"2 camas. Baño privado en el alojamiento"
            ,"2horas"
            ,4.5f)
        )
        lstSong.add(
            PaseoModel(R.drawable.img2
                ,R.drawable.img2
                ,"Vivienda rentada entero en Brașov, Rumanía"
                ,"6 huéspedes2 habitaciones2 camas2 baños"
                ,"24horas"
                ,2.5f)
        )
        lstSong.add(
            PaseoModel(R.drawable.img3
                ,R.drawable.img3
                ,"Villa entero en Agios Georgios, Grecia"
                ,"4 huéspedes2 habitaciones2 camas2 baños"
                ,"22horas"
                ,3.4f)
        )
        lstSong.add(
            PaseoModel(R.drawable.img4
                ,R.drawable.img4
                ,"Minicasa en Varlaam, Rumanía"
                ,"2 camas. Baño privado en el alojamiento"
                ,"12horas"
                ,4.5f)
        )
        return lstSong
    }


}