package com.univpm.gameon.data.collections.enums

enum class CardProvider(val displayName: String) {
    VISA("Visa"),
    MASTERCARD("Mastercard"),
    AMEX("American Express");

    override fun toString(): String = displayName
}
