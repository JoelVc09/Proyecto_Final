package dev.neil.proyecto_final.Nav.model

import java.io.Serializable

data class PaseoModel (
    var documentId: String,
    var imagenFondo: String,
    var imagenEmpresa:String,
    var nombrePaseo:String,
    var descripcionPaseo:String,
    var tiempo:Int,
    var rate: Float,
    var disponibilidad: String,
    var precios: String,
    var contacto: String,
    var comentarios: List<ComentarioModel> = emptyList(),
    var ivPaseo1: String,
    var ivPaseo2: String,
    var ivPaseo3: String
): Serializable