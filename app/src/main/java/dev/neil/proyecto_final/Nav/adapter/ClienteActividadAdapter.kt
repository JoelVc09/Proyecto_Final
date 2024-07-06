package dev.neil.proyecto_final.Nav.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.neil.proyecto_final.Nav.model.ClienteActividadModel
import dev.neil.proyecto_final.R

class ClienteActividadAdapter(private var clientes: List<ClienteActividadModel>) :
    RecyclerView.Adapter<ClienteActividadAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombreCliente: TextView = view.findViewById(R.id.tvNombreCliente)
        val tvEmailCliente: TextView = view.findViewById(R.id.tvEmailCliente)
        val tvCelularCliente: TextView = view.findViewById(R.id.tvCelularCliente)
        val tvFechaHorario: TextView = view.findViewById(R.id.tvFechaHorario)
        val tvNumPersonas: TextView = view.findViewById(R.id.tvNumPersonas)
        val tvPrecio: TextView = view.findViewById(R.id.tvPrecio)
        val tvNombrePaseo: TextView = view.findViewById(R.id.tvNombrePaseo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cliente_actividad, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.tvNombreCliente.text = cliente.fullName
        holder.tvEmailCliente.text = cliente.email
        holder.tvCelularCliente.text = cliente.numeroCelular
        holder.tvFechaHorario.text = "${cliente.fecha} ${cliente.horario}"
        holder.tvNumPersonas.text = "Personas: ${cliente.numPersonas}"
        holder.tvPrecio.text = "Precio: S/.${cliente.precio}"
        holder.tvNombrePaseo.text = cliente.nombrePaseo
    }

    override fun getItemCount() = clientes.size

    fun updateClientes(newClientes: List<ClienteActividadModel>) {
        clientes = newClientes
        notifyDataSetChanged()
    }
}