package com.univpm.gameon.core

import com.univpm.gameon.data.dao.impl.CampoDaoImpl
import com.univpm.gameon.data.dao.impl.CartaDaoImpl
import com.univpm.gameon.data.dao.impl.PrenotazioneDaoImpl
import com.univpm.gameon.data.dao.impl.RecensioneDaoImpl
import com.univpm.gameon.data.dao.impl.StrutturaDaoImpl
import com.univpm.gameon.data.dao.impl.UserDaoImpl
import com.univpm.gameon.data.dao.interfaces.CampoDao
import com.univpm.gameon.data.dao.interfaces.CartaDao
import com.univpm.gameon.data.dao.interfaces.PrenotazioneDao
import com.univpm.gameon.data.dao.interfaces.RecensioneDao
import com.univpm.gameon.data.dao.interfaces.StrutturaDao
import com.univpm.gameon.data.dao.interfaces.UserDao
import com.univpm.gameon.repositories.CampoRepository
import com.univpm.gameon.repositories.CartaRepository
import com.univpm.gameon.repositories.PrenotazioneRepository
import com.univpm.gameon.repositories.RecensioneRepository
import com.univpm.gameon.repositories.StrutturaRepository
import com.univpm.gameon.repositories.UserRepository
import com.univpm.gameon.repositories.impl.CampoRepositoryImpl
import com.univpm.gameon.repositories.impl.CartaRepositoryImpl
import com.univpm.gameon.repositories.impl.PrenotazioneRepositoryImpl
import com.univpm.gameon.repositories.impl.RecensioneRepositoryImpl
import com.univpm.gameon.repositories.impl.StrutturaRepositoryImpl
import com.univpm.gameon.repositories.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDao(): UserDao {
        return UserDaoImpl()
    }

    @Provides
    @Singleton
    fun provideCampoDao(): CampoDao {
        return CampoDaoImpl()
    }

    @Provides
    @Singleton
    fun provideCartaDao(): CartaDao {
        return CartaDaoImpl()
    }

    @Provides
    @Singleton
    fun providePrenotazioneDao(): PrenotazioneDao {
        return PrenotazioneDaoImpl()
    }

    @Provides
    @Singleton
    fun provideRecensioneDao(): RecensioneDao {
        return RecensioneDaoImpl()
    }

    @Provides
    @Singleton
    fun provideStrutturaDao(): StrutturaDao {
        return StrutturaDaoImpl()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository = UserRepositoryImpl(userDao)

    @Provides
    @Singleton
    fun provideCampoRepository(campoDao: CampoDao): CampoRepository = CampoRepositoryImpl(campoDao)

    @Provides
    @Singleton
    fun provideCartaRepository(cartaDao: CartaDao): CartaRepository = CartaRepositoryImpl(cartaDao)

    @Provides
    @Singleton
    fun providePrenotazioneRepository(prenotazioneDao: PrenotazioneDao): PrenotazioneRepository = PrenotazioneRepositoryImpl(prenotazioneDao)

    @Provides
    @Singleton
    fun provideRecensioneRepository(recensioneDao: RecensioneDao): RecensioneRepository = RecensioneRepositoryImpl(recensioneDao)

    @Provides
    @Singleton
    fun provideStrutturaRepository(strutturaDao: StrutturaDao): StrutturaRepository = StrutturaRepositoryImpl(strutturaDao)

}
