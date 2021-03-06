package com.multithread.car.domain.repository

import com.multithread.car.base.*
import com.multithread.car.base.domain.Answer
import com.multithread.car.base.domain.ObservableRepository
import com.multithread.car.domain.entity.CarEntity
import io.reactivex.Observable
import javax.inject.Inject

class CarListRepository @Inject constructor(
    private val localDataSource: ObservableReadableAndWriteable<List<CarEntity>, CarsParam>,
    private val remoteCarsDataSource: ObservableReadable<List<CarEntity>, CarsParam>
): ObservableRepository<List<CarEntity>, CarsParam>(){
    override fun getOffline(param: CarsParam): Observable<Answer<List<CarEntity>>> =
        localDataSource.read(param)

    override fun getRemote(param: CarsParam): Observable<Answer<List<CarEntity>>> =
        remoteCarsDataSource.read(param).doOnNext {
            localDataSource.write(it.extractData())
        }
}