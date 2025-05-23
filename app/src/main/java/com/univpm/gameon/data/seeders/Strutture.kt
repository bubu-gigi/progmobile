package com.univpm.gameon.data.seeders


data class Struttura(
    val nome: String,
    val citta: String,
    val sportDisponibili: List<String>
)

val strutture = listOf(
    Struttura("Sport District Milano", "Milano", listOf("Calcio a 5", "Padel (doppio)", "Beach volley (2vs2)")),
    Struttura("Arena Polivalente Torino", "Torino", listOf("Calcio a 8", "Tennis (singolo & doppio)")),
    Struttura("Multisport Village Roma", "Roma", listOf("Calcio a 5", "Padel (singolo & doppio)", "Tennis (doppio)")),
    Struttura("Active Hub Firenze", "Firenze", listOf("Tennis (singolo)", "Padel (singolo)", "Beach volley (2vs2)")),
    Struttura("PlayZone Napoli", "Napoli", listOf("Calcio a 5", "Beach volley (3vs3)")),
    Struttura("Energy Park Bologna", "Bologna", listOf("Calcio a 8", "Tennis (doppio)", "Beach volley (2vs2)")),
    Struttura("Sporting Life Verona", "Verona", listOf("Padel (doppio)", "Tennis (singolo)", "Beach volley (2vs2)")),
    Struttura("Urban Courts Genova", "Genova", listOf("Tennis (singolo)", "Beach volley (2vs2)")),
    Struttura("MatchPoint Bari", "Bari", listOf("Padel (singolo & doppio)", "Beach volley (3vs3)")),
    Struttura("Sporting Club Venezia", "Venezia", listOf("Calcio a 5", "Tennis (doppio)")),
    Struttura("TotalPlay Palermo", "Palermo", listOf("Calcio a 8", "Padel (doppio)")),
    Struttura("NextSport Lecce", "Lecce", listOf("Padel (singolo)", "Beach volley (2vs2)")),
    Struttura("AllCourt Catania", "Catania", listOf("Tennis (singolo & doppio)", "Beach volley (2vs2)")),
    Struttura("FairPlay Reggio Emilia", "Reggio Emilia", listOf("Calcio a 5", "Padel (doppio)")),
    Struttura("Oasi dello Sport Trento", "Trento", listOf("Tennis (singolo)", "Padel (singolo & doppio)")),
    Struttura("Sport Hub Ancona", "Ancona", listOf("Calcio a 5", "Padel (singolo & doppio)", "Tennis (singolo)")),
    Struttura("Centro Sportivo Piceno", "Ascoli Piceno", listOf("Calcio a 5 & Calcio a 8", "Tennis (singolo & doppio)")),
    Struttura("Action Sport Padova", "Padova", listOf("Calcio a 5", "Padel (singolo & doppio)", "Tennis (singolo)")),
    Struttura("GreenPark Pescara", "Pescara", listOf("Padel (doppio)", "Beach volley (2vs2)", "Tennis (singolo & doppio)")),
    Struttura("SkySport Perugia", "Perugia", listOf("Calcio a 5", "Padel (singolo)", "Beach volley (3vs3)"))
)
