package com.univpm.gameon.data.collections.enums

enum class TipologiaTerreno(val displayName: String) {
    ERBA_SINTETICA("Erba Sintetica"),
    CEMENTO("Cemento");

    override fun toString(): String = displayName
}
