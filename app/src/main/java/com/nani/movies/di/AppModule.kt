package com.nani.movies.di

import com.nani.movies.BuildConfig
import com.nani.movies.data.remote.api.MovieApi
import com.nani.movies.data.repository.MovieRepositoryImpl
import com.nani.movies.domain.repository.MovieRepository
import com.nani.movies.domain.usecase.GetMovieByIdUseCase
import com.nani.movies.domain.usecase.GetMoviesUseCase
import com.nani.movies.presentation.viewmodel.MovieDetailViewModel
import com.nani.movies.presentation.viewmodel.MovieViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
    
    // Network
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${BuildConfig.TMDB_API_TOKEN}"
                    )
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
    
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }
    
    // Repository
    single<MovieRepository> {
        MovieRepositoryImpl(get())
    }

    // Use Cases
    single {
        GetMoviesUseCase(get())
    }

    single {
        GetMovieByIdUseCase(get())
    }

    // ViewModels
    viewModel {
        MovieViewModel(get())
    }

    viewModel {
        MovieDetailViewModel(get())
    }
}