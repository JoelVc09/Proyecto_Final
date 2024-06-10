package dev.neil.proyecto_final.Nav.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import dev.neil.proyecto_final.R

class HomeViewModel(private var lstGEN: List<Adapter>) :
    RecyclerView.Adapter<HomeViewModel.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val backGroundImage : ImageView = itemView.findViewById(R.id.BackGroundImage)
        val principalImage : ImageView = itemView.findViewById(R.id.PrincipalImage)
        val tvTiempo : TextView = itemView.findViewById(R.id.tvTiempo)
        val tvDescrPas : TextView = itemView.findViewById(R.id.tvDescrPas)
        val tvNombPaseo : TextView = itemView.findViewById(R.id.tvNombPaseo)
        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewModel.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater
                .inflate(R.layout.fragment_home
                    ,parent
                    ,false)

        )
    }

    override fun onBindViewHolder(holder: HomeViewModel.ViewHolder, position: Int) {
        val itemsong = lstGEN[position]
        holder.backGroundImage.setImageResource(itemsong.imagefondo)
        holder.principalImage.setImageResource(itemsong.imageEmpresa)
        holder.tvTiempo.text = itemsong.Tiempo
        holder.tvDescrPas.text =  itemsong.DesripcionPaseo
        holder.tvNombPaseo.text =  itemsong.NombrePaseo
        holder.ratingBar.rating = itemsong.Rate

    }

    override fun getItemCount(): Int {
        return lstGEN.size
    }

}