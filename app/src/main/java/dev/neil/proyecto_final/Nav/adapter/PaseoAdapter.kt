package dev.neil.proyecto_final.Nav.adapter

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
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R

class PaseoAdapter(private var lstGEN: List<PaseoModel>) :
    RecyclerView.Adapter<PaseoAdapter.ViewHolder>(){
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val backGroundImage : ImageView = itemView.findViewById(R.id.BackGroundImage)
        val principalImage : ImageView = itemView.findViewById(R.id.PrincipalImage)
        val tvTiempo : TextView = itemView.findViewById(R.id.tvTiempo)
        val tvDescrPas : TextView = itemView.findViewById(R.id.tvDescrPas)
        val tvNombPaseo : TextView = itemView.findViewById(R.id.tvNombPaseo)
        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater
                .inflate(R.layout.item_paseo
                    ,parent
                    ,false)

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsong = lstGEN[position]
        holder.backGroundImage.setImageResource(itemsong.imagefondo)
        holder.principalImage.setImageResource(itemsong.imageEmpresa)
        holder.tvTiempo.text = itemsong.tiempo
        holder.tvDescrPas.text =  itemsong.desripcionPaseo
        holder.tvNombPaseo.text =  itemsong.nombrePaseo
        holder.ratingBar.rating = itemsong.rate

    }

    override fun getItemCount(): Int {
        return lstGEN.size
    }

}