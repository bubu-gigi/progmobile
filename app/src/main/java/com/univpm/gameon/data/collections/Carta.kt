package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.CardProvider

data class Carta(
    val id: String = "",
    val userId: String = "",
    val cardHolderName: String = "",
    val cardNumber: String = "",
    val expirationMonth: Int = 1,
    val expirationYear: Int = 2025,
    val cvv: String = "",
    val provider: CardProvider = CardProvider.VISA
)