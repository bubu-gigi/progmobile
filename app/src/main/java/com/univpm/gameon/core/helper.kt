package com.univpm.gameon.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun validateEmail(email: String): String? {
    if (email.isBlank()) return "Il campo email è obbligatorio."
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
    if (!emailRegex.matches(email)) return "Inserisci un indirizzo email valido."
    return null
}

fun validatePassword(password: String): String? {
    if (password.isBlank()) return "Il campo password è obbligatorio."
    if (password.length < 6) return "La password deve contenere almeno 6 caratteri."
    if (!password.any { it.isLetter() }) return "La password deve contenere almeno una lettera."
    if (!password.any { it.isDigit() }) return "La password deve contenere almeno un numero."
    return null
}

fun checkFieldLength(fieldValue: String, minLength: Int, fieldName: String): String? {
    return when {
        fieldValue.isBlank() -> "Il campo $fieldName è obbligatorio"
        fieldValue.length < minLength -> "Il campo $fieldName deve contenere almeno $minLength caratteri"
        else -> null
    }
}

fun validateCodiceFiscale(cf: String): String? {
    val regex = Regex("^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$")
    return when {
        cf.isBlank() -> "Inserisci il codice fiscale"
        !regex.matches(cf.uppercase()) -> "Codice fiscale non valido"
        else -> null
    }
}

fun giornoLabel(numero: Int): String {
    return listOf("Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato", "Domenica")
        .getOrNull(numero - 1) ?: "?"
}

@RequiresApi(Build.VERSION_CODES.O)
fun generaSlotOrari(inizio: String, fine: String): List<Pair<String, String>> {
    val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())

    val startTime = LocalTime.parse(inizio, formatter)
    val endTime = LocalTime.parse(fine, formatter)

    val slots = mutableListOf<Pair<String, String>>()

    var current = startTime
    while (current < endTime) {
        val next = current.plusHours(1)
        if (next > endTime) break
        slots.add(current.format(formatter) to next.format(formatter))
        current = next
    }

    return slots
}

@RequiresApi(Build.VERSION_CODES.O)
fun raggruppaSlotConsecutivi(slots: List<Pair<String, String>>): List<Pair<String, String>> {
    val ordinati = slots.map { LocalTime.parse(it.first) to LocalTime.parse(it.second) }
        .sortedBy { it.first }

    if (ordinati.isEmpty()) return emptyList()

    val gruppi = mutableListOf<MutableList<Pair<LocalTime, LocalTime>>>()
    var gruppoCorrente = mutableListOf(ordinati.first())

    for (i in 1 until ordinati.size) {
        val precedente = gruppoCorrente.last()
        val corrente = ordinati[i]

        if (precedente.second == corrente.first) {
            gruppoCorrente.add(corrente)
        } else {
            gruppi.add(gruppoCorrente)
            gruppoCorrente = mutableListOf(corrente)
        }
    }
    gruppi.add(gruppoCorrente)

    return gruppi.map { it.first().first.toString() to it.last().second.toString() }
}


