package com.univpm.gameon.repositories

import com.univpm.gameon.data.collections.Campo

interface CampoRepository {
    suspend fun getCampiByStrutturaId(strutturaId: String): List<Campo>
    suspend fun getCampo(id: String): Campo?
    suspend fun saveCampo(campo: Campo): Boolean
    suspend fun updateCampo(id: String, campo: Campo): Boolean
    suspend fun deleteCampo(id: String): Boolean
}
