package dev.neil.proyecto_final.Nav.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.neil.proyecto_final.R
import dev.neil.proyecto_final.model.HistorialClienteModel

class HistorialClienteAdapter(private var lstHistoria: List<HistorialClienteModel>) :
    RecyclerView.Adapter<HistorialClienteAdapter.HistoriaViewHolder>(){

    class HistoriaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivHistorial : ImageView = itemView.findViewById(R.id.ivHistorial)
        val tvFechaActividad : TextView = itemView.findViewById(R.id.tvFechaActividad)
        val tvNombreActividadHistorial : TextView = itemView.findViewById(R.id.tvNombreActividadHistorial)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HistoriaViewHolder(
            layoutInflater.inflate(R.layout.historial_cliente_item, parent, false)
        )
    }

    fun updateItems(newItems: List<HistorialClienteModel>) {
        lstHistoria = newItems
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HistoriaViewHolder, position: Int) {
        val itemHistoria = lstHistoria[position]
        holder.tvNombreActividadHistorial.text = itemHistoria.nombrePaseo
        holder.tvFechaActividad.text = itemHistoria.fecha
        Picasso.get().load(itemHistoria.imagen).into(holder.ivHistorial)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(lstHistoria[position])
        }
    }

    override fun getItemCount(): Int {
        return lstHistoria.size
    }
    private var onItemClickListener: ((HistorialClienteModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (HistorialClienteModel) -> Unit) {
        onItemClickListener = listener
    }

}