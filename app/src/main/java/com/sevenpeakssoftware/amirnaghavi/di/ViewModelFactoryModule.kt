package com.sevenpeakssoftware.amirnaghavi.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sevenpeakssoftware.amirnaghavi.presentation.car.CarsViewModel
import com.sevenpeakssoftware.amirnaghavi.util.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CarsViewModel::class)
    abstract fun bindViewModel(viewModel: CarsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

}
