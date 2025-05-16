package com.univpm.gameon.data.dao.interfaces

import com.univpm.gameon.data.collections.Campo

interface CampoDao {
    suspend fun getCampiByStrutturaId(strutturaId: String): List<Campo>
    suspend fun getCampoById(id: String): Campo?
    suspend fun addCampo(campo: Campo): Boolean
    suspend fun updateCampo(id: String, campo: Campo): Boolean
    suspend fun deleteCampo(id: String): Boolean
}