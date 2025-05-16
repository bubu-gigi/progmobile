package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.CampoTipologia
import com.univpm.gameon.data.collections.enums.TipologiaTerreno

data class Campo(
    val nomeCampo: String = "",
    val tipologia: CampoTipologia,
    val tipologiaTerreno: TipologiaTerreno = TipologiaTerreno.ERBA_SINTETICA,
    val prezzoOrario: Double = 0.0,
    val numeroGiocatori: Int = 0,
    val spogliatoi: Boolean = false,
    val strutturaId: String
)
