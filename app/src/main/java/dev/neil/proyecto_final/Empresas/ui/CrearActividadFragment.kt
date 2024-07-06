package dev.neil.proyecto_final.Empresas.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.neil.proyecto_final.Nav.model.ComentarioModel
import dev.neil.proyecto_final.R
import java.io.Serializable


class CrearActividadFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_crear_actividad, container, false)
        val spnCategoria: Spinner = view.findViewById(R.id.spnCategoria)
        val etMaxPer: EditText = view.findViewById(R.id.etMaxPer)
        val etBack: EditText = view.findViewById(R.id.etBack)
        val etLogo: EditText = view.findViewById(R.id.etLogo)
        val etImAd1: EditText = view.findViewById(R.id.etImAd1)
        val etImAd2: EditText = view.findViewById(R.id.etImAd2)
        val etImAd3: EditText = view.findViewById(R.id.etImAd3)
        val etTituloActividad: EditText = view.findViewById(R.id.etTituloActividad)
        val rbInmediato: RadioButton = view.findViewById(R.id.rbInmediata)
        val rbPreAp: RadioButton = view.findViewById(R.id.rbPreAp)
        val etPrecioActividad: EditText = view.findViewById(R.id.etPrecioActividad)
        val etTelefono: EditText = view.findViewById(R.id.etTelefono)
        val btnPublicar: Button = view.findViewById(R.id.btnPublicar)
        val etDescripcion: EditText = view.findViewById(R.id.etDescrip)
        var tipoConfirmacion = ""
        if (rbInmediato.isChecked) {
            tipoConfirmacion = rbInmediato.text.toString()
        } else if (rbPreAp.isChecked) {
            tipoConfirmacion = rbPreAp.text.toString()
        }
        btnPublicar.setOnClickListener {
            var tipoConfirmacion = tipoConfirmacion
            var imagenFondo = etBack.text.toString()
            var imagenEmpresa = etLogo.text.toString()
            var nombrePaseo = etTituloActividad.text.toString()
            var descripcionPaseo = etDescripcion.text.toString()
            var precios = etPrecioActividad.text.toString()
            var contacto = etTelefono.text.toString()
            var ivPaseo1 = etImAd1.text.toString()
            var ivPaseo2 = etImAd2.text.toString()
            var ivPaseo3 = etImAd3.text.toString()
            var grupoMax = etMaxPer.text.toString().toInt()
            val auth = FirebaseAuth.getInstance()
            val userId = auth.currentUser?.uid
            val uid = userId?: ""
            subirDatos(tipoConfirmacion, imagenFondo, imagenEmpresa, nombrePaseo, descripcionPaseo, precios, contacto, ivPaseo1, ivPaseo2, ivPaseo3, grupoMax, uid)
        }

        return view
    }
    private fun subirDatos(
        tipoConfirmacion: String,
        imagenFondo: String,
        imagenEmpresa: String,
        nombrePaseo: String,
        descripcionPaseo: String,
        precios: String,
        contacto: String,
        ivPaseo1: String,
        ivPaseo2: String,
        ivPaseo3: String,
        grupoMax: Int,
        uid: String,
    ){
        val db = FirebaseFirestore.getInstance()
        val datosPaseo = hashMapOf(
            "tipoConfirmacion" to tipoConfirmacion,
            "imagenFondo" to imagenFondo,
            "imagenEmpresa" to imagenEmpresa,
            "nombrePaseo" to nombrePaseo,
            "descripcionPaseo" to descripcionPaseo,
            "precios" to precios,
            "contacto" to contacto,
            "ivPaseo1" to ivPaseo1,
            "ivPaseo2" to ivPaseo2,
            "ivPaseo3" to ivPaseo3,
            "grupoMax" to grupoMax,
            "uid" to uid
        )
        db.collection("paseos")
            .add(datosPaseo)
            .addOnSuccessListener { documentReference ->
                Log.d("CrearActividadFragment", "Datos subidos a Firestore con ID: ${documentReference.id}")
                findNavController().navigate(R.id.action_crearActividadFragment_to_nav_home_Empresas)
                Snackbar.make(
                    requireView(),
                    "Actividad creada con éxito",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            .addOnFailureListener { e ->
                Log.e("CrearActividadFragment", "Error al subir datos a Firestore", e)
                // Show error message
                Snackbar.make(
                    requireView(),
                    "Error al crear la actividad",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }
}
