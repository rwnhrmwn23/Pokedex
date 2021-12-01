package com.onedev.pokedex.core.di

import androidx.room.Room
import com.onedev.pokedex.core.data.source.PokemonRepository
import com.onedev.pokedex.core.data.source.local.LocalDataSource
import com.onedev.pokedex.core.data.source.local.room.PokemonDatabase
import com.onedev.pokedex.core.data.source.remote.RemoteDataSource
import com.onedev.pokedex.core.data.source.remote.network.ApiService
import com.onedev.pokedex.core.domain.repository.IPokemonRepository
import com.onedev.pokedex.core.domain.usecase.PokemonInteractor
import com.onedev.pokedex.core.domain.usecase.PokemonUseCase
import com.onedev.pokedex.ui.fragment.detail.viewmodel.DetailViewModel
import com.onedev.pokedex.ui.fragment.favorite.viewmodel.FavoriteViewModel
import com.onedev.pokedex.ui.fragment.home.viewmodel.HomeViewModel
import com.onedev.pokedex.utils.Constant.BASE_URL
import com.onedev.pokedex.utils.Constant.DATABASE_NAME
import com.onedev.pokedex.utils.Constant.HOSTNAME
import com.onedev.pokedex.utils.Constant.HOSTNAME_PINS1
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<PokemonDatabase>().pokemonDao() }
    single {
        Room.databaseBuilder(androidContext(), PokemonDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = HOSTNAME
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, HOSTNAME_PINS1)
            .build()
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val useCaseModule = module {
    factory<PokemonUseCase> { PokemonInteractor(get()) }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IPokemonRepository> {
        PokemonRepository(get(), get())
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}