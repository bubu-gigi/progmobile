package com.univpm.gameon.data.seeders

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.univpm.gameon.data.collections.Struttura
import com.univpm.gameon.data.collections.enums.Sport

val strutture = listOf(
    Struttura("",
        "Sport District Milano", "Via Sportiva 1", "Milano",
        45.4642, 9.19,
        listOf(Sport.CALCIO5, Sport.PADEL, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "Arena Polivalente Torino", "Via Sportiva 2", "Torino",
        45.0703, 7.6869,
        listOf(Sport.CALCIO8, Sport.TENNIS),
    ),
    Struttura("",
        "Multisport Village Roma", "Via Sportiva 3", "Roma",
        41.9028, 12.4964,
        listOf(Sport.CALCIO5, Sport.PADEL, Sport.TENNIS),
    ),
    Struttura("",
        "Active Hub Firenze", "Via Sportiva 4", "Firenze",
        43.7696, 11.2558,
        listOf(Sport.TENNIS, Sport.PADEL, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "PlayZone Napoli", "Via Sportiva 5", "Napoli",
        40.8522, 14.2681,
        listOf(Sport.CALCIO5, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "Energy Park Bologna", "Via Sportiva 6", "Bologna",
        44.4949, 11.3426,
        listOf(Sport.CALCIO8, Sport.TENNIS, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "Sporting Life Verona", "Via Sportiva 7", "Verona",
        45.4384, 10.9916,
        listOf(Sport.PADEL, Sport.TENNIS, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "Urban Courts Genova", "Via Sportiva 8", "Genova",
        44.4056, 8.9463,
        listOf(Sport.TENNIS, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "MatchPoint Bari", "Via Sportiva 9", "Bari",
        41.1171, 16.8719,
        listOf(Sport.PADEL, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "Sporting Club Venezia", "Via Sportiva 10", "Venezia",
        45.4408, 12.3155,
        listOf(Sport.CALCIO5, Sport.TENNIS),
    ),
    Struttura("",
        "TotalPlay Palermo", "Via Sportiva 11", "Palermo",
        38.1157, 13.3615,
        listOf(Sport.CALCIO8, Sport.PADEL),
    ),
    Struttura("",
        "NextSport Lecce", "Via Sportiva 12", "Lecce",
        40.3529, 18.1743,
        listOf(Sport.PADEL, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "AllCourt Catania", "Via Sportiva 13", "Catania",
        37.5079, 15.083,
        listOf(Sport.TENNIS, Sport.BEACH_VOLLEY),
    ),
    Struttura("",
        "FairPlay Reggio Emilia", "Via Sportiva 14", "Reggio Emilia",
        44.6983, 10.6308,
        listOf(Sport.CALCIO5, Sport.PADEL),
    ),
    Struttura("",
        "Oasi dello Sport Trento", "Via Sportiva 15", "Trento",
        46.0707, 11.1211,
        listOf(Sport.TENNIS, Sport.PADEL),
    ),
    Struttura("",
        "Sport Hub Ancona", "Via Sportiva 16", "Ancona",
        43.6158, 13.5189,
        listOf(Sport.CALCIO5, Sport.PADEL, Sport.TENNIS),
    ),
    Struttura("",
        "Centro Sportivo Piceno", "Via Sportiva 17", "Ascoli Piceno",
        42.8532, 13.574,
        listOf(Sport.CALCIO5, Sport.CALCIO8, Sport.TENNIS),
    ),
    Struttura("",
        "Action Sport Padova", "Via Sportiva 18", "Padova",
        45.4064, 11.8768,
        listOf(Sport.CALCIO5, Sport.PADEL, Sport.TENNIS),
    ),
    Struttura("",
        "GreenPark Pescara", "Via Sportiva 19", "Pescara",
        42.4618, 14.2161,
        listOf(Sport.PADEL, Sport.BEACH_VOLLEY, Sport.TENNIS),
    ),
    Struttura("",
        "SkySport Perugia", "Via Sportiva 20", "Perugia",
        43.1107, 12.3908,
        listOf(Sport.CALCIO5, Sport.PADEL, Sport.BEACH_VOLLEY),
    )
)

fun inserisciMockStrutture() {
    val db = Firebase.firestore

    strutture.forEach { struttura ->
        val strutturaRef = db.collection("strutture").document()
        val strutturaConId = struttura.copy(id = strutturaRef.id)

        strutturaRef.set(strutturaConId)
            .addOnSuccessListener {
                println("✅ Inserita struttura: ${strutturaConId.nome}")
            }
            .addOnFailureListener { e ->
                println("❌ Errore durante l'inserimento: ${e.message}")
            }
    }
}