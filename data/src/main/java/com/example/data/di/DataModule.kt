package com.example.data.di

import android.content.Context
import com.example.data.jsonSerializers.HabitJsonDeserializer
import com.example.data.jsonSerializers.HabitJsonSerializer
import com.example.data.jsonSerializers.HabitUIDJsonDeserializer
import com.example.data.repository.RepositoryImpl
import com.example.data.service.HabitsService
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitUID
import com.example.domain.repository.Repository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

private const val TOKEN: String = "23f80e7b-7e5d-4e9d-b072-03d900e4a9b3"

@Module
class DataModule { // добавить в один компонент с DomainModule, чтобы в нем был доступен метод провайда репозитория

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor() : Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val builder = originalRequest.newBuilder().header(
                    "Authorization", TOKEN
                )

                val newRequest = builder.build()
                return chain.proceed(newRequest)
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authorizationInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor ) : OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson() : Gson {
        return GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
            .registerTypeAdapter(HabitUID::class.java, HabitUIDJsonDeserializer())
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient : OkHttpClient, gson : Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/") // перенести в константы
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit : Retrofit) : HabitsService {
        return retrofit.create(HabitsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(context : Context, habitsService: HabitsService) : Repository { // убрать зависимоть модуля от дата-слоя?
        return RepositoryImpl(context, habitsService)
    }

}