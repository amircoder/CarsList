package com.sevenpeakssoftware.amirnaghavi.presentation.car.handler

import com.sevenpeakssoftware.amirnaghavi.base.BaseUIHandler
import com.sevenpeakssoftware.amirnaghavi.base.CarsParam
import com.sevenpeakssoftware.amirnaghavi.base.ErrorEntity
import com.sevenpeakssoftware.amirnaghavi.databinding.FragmentCarBinding
import com.sevenpeakssoftware.amirnaghavi.domain.entity.CarEntity
import com.sevenpeakssoftware.amirnaghavi.extension.show
import com.sevenpeakssoftware.amirnaghavi.presentation.car.CarState
import com.sevenpeakssoftware.amirnaghavi.presentation.car.CarsListAdapter
import com.sevenpeakssoftware.amirnaghavi.presentation.car.CarsViewModel
import com.sevenpeakssoftware.amirnaghavi.presentation.car.GetCarInfoEvent
import com.sevenpeakssoftware.amirnaghavi.util.StringResourceHolder

class CarUIHandler(binding: FragmentCarBinding,
                   viewModel: CarsViewModel,
                   private val stringResourceHolder: StringResourceHolder) :
        BaseUIHandler<CarState, CarsViewModel, FragmentCarBinding>(viewModel, binding) {

    private val carListAdapter by lazy {
        CarsListAdapter()
    }

    override fun startUp(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        viewModel.handleEvent(GetCarInfoEvent(), CarsParam())
    }

    override fun bindUiState(state: CarState, binding: FragmentCarBinding, viewModel: CarsViewModel) {
        setupRecyclerView(binding)
        initListeners(binding, viewModel)
        when (state.data) {
            CarState.Data.Idle -> {
                // do nothing
            }
            CarState.Data.NoData -> {
                handleNoData(binding)
            }
            is CarState.Data.Cars -> {
                handleData((state.data as CarState.Data.Cars).cars, binding)
            }
        }
    }

    private fun handleData(cars: List<CarEntity>, binding: FragmentCarBinding) {
        binding.noDataContainer.noDataContainer.show(false)
        binding.carListContainer.show(true)
        carListAdapter.itemList = cars
    }

    private fun handleNoData(binding: FragmentCarBinding) {
        binding.noDataContainer.root.show(true)
        binding.carListContainer.show(false)
    }

    private fun initListeners(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        binding.noDataContainer.noDataTryAgainButton.setOnClickListener {
            viewModel.handleEvent(GetCarInfoEvent(), CarsParam())
        }
    }

    private fun setupRecyclerView(binding: FragmentCarBinding) {
        binding.carList.adapter = carListAdapter
    }

    override fun handleError(error: ErrorEntity, binding: FragmentCarBinding) {
        binding.noDataContainer.root.show(true)
        binding.carListContainer.show(false)
    }

    override fun handleLoading(loading: Boolean, binding: FragmentCarBinding) {
        binding.carLoader.show(loading)
    }

    override fun onResume(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        // do nothing
    }

    override fun onStart(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        // do nothing
    }

    override fun onPause(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        // do nothing
    }

    override fun onStop(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        // do nothing
    }

    override fun onDestroy(binding: FragmentCarBinding, viewModel: CarsViewModel) {
        // do nothing
    }
}