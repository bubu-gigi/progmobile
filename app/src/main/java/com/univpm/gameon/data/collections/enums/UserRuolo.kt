package com.univpm.gameon.data.collections.enums

enum class UserRuolo(val displayName: String) {
    GIOCATORE("Giocatore"),
    ADMIN("Admin");

    override fun toString(): String = displayName
}