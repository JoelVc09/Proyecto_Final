package dev.neil.proyecto_final.Nav.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import dev.neil.proyecto_final.Nav.adapter.ComentarioAdapter
import dev.neil.proyecto_final.Nav.model.ComentarioModel
import dev.neil.proyecto_final.Nav.model.PaseoModel
import dev.neil.proyecto_final.R

class PaseoDetailsFragment : Fragment() {
    private var comentarios: List<ComentarioModel> = listOf()
    private lateinit var paseoId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_paseo_details, container, false)
        val paseo = arguments?.getSerializable("PASEO_KEY") as? PaseoModel
        val ivPortada: ImageView = view.findViewById(R.id.ivPortada)
        val ivLogo: ImageView = view.findViewById(R.id.ivLogo)
        val tvNombreActividad: TextView = view.findViewById(R.id.tvNombreActividad)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val tvTiempoParaNext: TextView = view.findViewById(R.id.tvTiempoParaNext)
        val rbarActividad: RatingBar = view.findViewById(R.id.rbarActividad)
        val tvDisponibilidad: TextView = view.findViewById(R.id.tvDisponibilidad)
        val tvPrecios: TextView = view.findViewById(R.id.tvPrecios)
        val tvContacto: TextView = view.findViewById(R.id.tvContacto)
        val rvComentarios: RecyclerView = view.findViewById(R.id.rvComentarios)
        val Paseo1: ImageView = view.findViewById(R.id.ivPaseo1)
        val Paseo2: ImageView = view.findViewById(R.id.ivPaseo2)
        val Paseo3: ImageView = view.findViewById(R.id.ivPaseo3)
        val btnIrAReservar: Button = view.findViewById(R.id.btnIrAReservar)

        paseoId = paseo?.documentId ?: ""
        Picasso.get().load(paseo?.imagenFondo ?: "").into(ivPortada)
        Picasso.get().load(paseo?.ivPaseo1 ?: "").into(Paseo1)
        Picasso.get().load(paseo?.ivPaseo2 ?: "").into(Paseo2)
        Picasso.get().load(paseo?.ivPaseo3 ?: "").into(Paseo3)
        Picasso.get().load(paseo?.imagenEmpresa ?: "").into(ivLogo)
        tvDisponibilidad.text = paseo?.disponibilidad ?: ""
        tvPrecios.text = paseo?.precios ?: ""
        tvContacto.text = paseo?.contacto ?: ""
        tvNombreActividad.text = paseo?.nombrePaseo ?: ""
        tvDescripcion.text = paseo?.descripcionPaseo ?: ""
        tvTiempoParaNext.text = paseo?.tiempo.toString() + " horas"
        rbarActividad.rating = paseo?.rate ?: 0f

        rvComentarios.layoutManager = LinearLayoutManager(requireContext())

        if (paseoId.isNotEmpty()) {
            fetchComentarios(paseoId) { comentariosList ->
                comentarios = comentariosList
                rvComentarios.adapter = ComentarioAdapter(comentarios)
            }
        } else {
            Log.e("PaseoDetailsFragment", "Paseo ID is empty")
        }

        return view
    }

    private fun fetchComentarios(paseoId: String, onComentariosFetched: (List<ComentarioModel>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("paseos").document(paseoId)

        docRef.collection("Comentarios").get().addOnSuccessListener { commentsSnapshot ->
            val comentarios = commentsSnapshot.documents.map { commentDocument ->
                ComentarioModel(
                    commentDocument["imagenPerfil"].toString(),
                    commentDocument["rate"].toString().toFloat(),
                    commentDocument["comentario"].toString()
                )
            }
            onComentariosFetched(comentarios)
        }.addOnFailureListener { error ->
            Log.e("PaseoDetailsFragment", "Error al obtener los comentarios de Firestore", error)
            onComentariosFetched(emptyList()) // Enviar una lista vacía en caso de error
        }
    }
}
