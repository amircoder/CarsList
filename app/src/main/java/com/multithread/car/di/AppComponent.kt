package com.multithread.car.di

import android.app.Application
import com.multithread.car.presentation.CarFragmentBinding
import com.multithread.car.presentation.NavigationActivityBinding
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidModule::class,
        MapperModule::class,
        ViewModelFactoryModule::class,
        DataSourceModule::class,
        PersistenceModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        NavigationActivityBinding::class,
        CarFragmentBinding::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)

}