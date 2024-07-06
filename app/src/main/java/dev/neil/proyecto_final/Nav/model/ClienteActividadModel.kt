package dev.neil.proyecto_final.Nav.model

data class ClienteActividadModel(
    val uid: String,
    val fullName: String,
    val email: String,
    val numeroCelular: String,
    val fecha: String,
    val horario: String,
    val numPersonas: Int,
    val precio: Double,
    val nombrePaseo: String
)