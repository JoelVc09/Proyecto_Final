package dev.neil.proyecto_final.Nav.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.neil.proyecto_final.Nav.model.ComentarioModel

import dev.neil.proyecto_final.R

class ComentarioAdapter(private var lstComentarios: List<ComentarioModel>) :
    RecyclerView.Adapter<ComentarioAdapter.ComentarioViewHolder>(){
    class ComentarioViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivPfp : ImageView = itemView.findViewById(R.id.ivPfp)
        val rbarComment : RatingBar = itemView.findViewById(R.id.rbarComment)
        val tvComment : TextView = itemView.findViewById(R.id.tvComment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComentarioViewHolder(
            layoutInflater.inflate(R.layout.item_comentario, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lstComentarios.size
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val itemComentario = lstComentarios[position]
        Picasso.get().load(itemComentario.imagenPerfil.toString()).into(holder.ivPfp)
        holder.rbarComment.rating = itemComentario.rate
        holder.tvComment.text = itemComentario.comentario
    }
}
