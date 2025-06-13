package com.univpm.gameon.data.collections

import com.univpm.gameon.data.collections.enums.Sport
import com.univpm.gameon.data.collections.enums.TipologiaTerreno

data class Campo(
    val id: String = "",
    val nomeCampo: String = "",
    val sport: Sport,
    val tipologiaTerreno: TipologiaTerreno = TipologiaTerreno.ERBA_SINTETICA,
    val prezzoOrario: Double = 0.0,
    val numeroGiocatori: Int = 0,
    val spogliatoi: Boolean = false,
    val disponibilitaSettimanale: List<TemplateGiornaliero>
)

data class TemplateGiornaliero(
    val giornoSettimana: Int, // 1 = lunedì, ..., 7 = domenica
    val orarioApertura: String,
    val orarioChiusura: String
)