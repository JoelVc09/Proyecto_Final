package dev.neil.proyecto_final.NavEmpresa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home_empresa, container, false)
        val rvListado: RecyclerView = view.findViewById(R.id.recyclerView3E)




        return view
    }


}