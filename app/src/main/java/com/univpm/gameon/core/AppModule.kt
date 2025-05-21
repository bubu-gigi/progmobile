package com.univpm.gameon.core

import com.univpm.gameon.data.dao.impl.*
import com.univpm.gameon.data.dao.interfaces.*
import com.univpm.gameon.repositories.*
import com.univpm.gameon.repositories.impl.*
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
    fun provideUserDao(): UserDao = UserDaoImpl()

    @Provides
    @Singleton
    fun provideCampoDao(): CampoDao = CampoDaoImpl()

    @Provides
    @Singleton
    fun provideCartaDao(): CartaDao = CartaDaoImpl()

    @Provides
    @Singleton
    fun providePrenotazioneDao(): PrenotazioneDao = PrenotazioneDaoImpl()

    @Provides
    @Singleton
    fun provideRecensioneDao(): RecensioneDao = RecensioneDaoImpl()

    @Provides
    @Singleton
    fun provideStrutturaDao(): StrutturaDao = StrutturaDaoImpl()

    @Provides
    @Singleton
    fun provideConversazioneDao(): ConversazioneDao = ConversazioneDaoImpl()

    @Provides
    @Singleton
    fun provideMessageDao(): MessaggioDao = MessaggioDaoImpl()

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
    fun providePrenotazioneRepository(prenotazioneDao: PrenotazioneDao): PrenotazioneRepository =
        PrenotazioneRepositoryImpl(prenotazioneDao)

    @Provides
    @Singleton
    fun provideRecensioneRepository(recensioneDao: RecensioneDao): RecensioneRepository =
        RecensioneRepositoryImpl(recensioneDao)

    @Provides
    @Singleton
    fun provideStrutturaRepository(strutturaDao: StrutturaDao): StrutturaRepository =
        StrutturaRepositoryImpl(strutturaDao)

    @Provides
    @Singleton
    fun provideConversazioneRepository(conversazioneDao: ConversazioneDao): ConversazioneRepository =
        ConversazioneRepositoryImpl(conversazioneDao)

    @Provides
    @Singleton
    fun provideMessaggioRepository(messageDao: MessaggioDao): MessaggioRepository =
        MessaggioRepositoryImpl(messageDao)
}
