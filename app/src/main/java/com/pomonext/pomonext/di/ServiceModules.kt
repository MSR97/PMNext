package com.pomonext.pomonext.di

import com.pomonext.pomonext.service.PomoRunForeGroundService
import com.pomonext.pomonext.service.impl.PomoRunForeGroundServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModules {
    @Binds
    abstract fun providePomoRunService(impl:PomoRunForeGroundServiceImpl):PomoRunForeGroundService

}