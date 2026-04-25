package com.Uma.byldwealth

import com.Uma.byldwealth.data.repository.HoldingsRepository
import com.Uma.byldwealth.data.repository.JsonHoldingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindHoldingsRepository(
        impl: JsonHoldingsRepository
    ): HoldingsRepository
}