package dev.neil.proyecto_final.Empresas.model

import dev.neil.proyecto_final.Nav.model.ComentarioModel
import java.io.Serializable

data class PaseoEmpresaModel(
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
    var ivPaseo3: String,
    var primerTurno: Int,
    var intervaloTiempo: Int,
    var grupoMax: String,
): Serializable
