package com.univpm.gameon.data.collections.enums

enum class Sport(val displayName: String) {
    CALCIO5("Calcio a 5"),
    CALCIO8("Calcio a 8"),
    TENNIS("Tennis"),
    PADEL("Padel"),
    BEACH_VOLLEY("Beach Volley");

    override fun toString(): String = displayName
}
