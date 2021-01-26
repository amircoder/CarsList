package com.sevenpeakssoftware.amirnaghavi.data.remote

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.sevenpeakssoftware.amirnaghavi.base.Answer
import com.sevenpeakssoftware.amirnaghavi.base.Mapper
import com.sevenpeakssoftware.amirnaghavi.data.CarsAPI
import com.sevenpeakssoftware.amirnaghavi.data.dto.CarsDTO
import com.sevenpeakssoftware.amirnaghavi.domain.entity.CarEntity
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteCarsDataSourceTest {

    @Mock
    lateinit var apiResponse: CarsDTO

    @Mock
    lateinit var carEntity: CarEntity

    @Mock
    lateinit var mockApi: CarsAPI

    @Mock
    lateinit var mockMapper: Mapper<CarsDTO, CarEntity>

    @InjectMocks
    lateinit var subject: RemoteCarsDataSource

    lateinit var observer: TestObserver<Answer<CarEntity>>

    @Test
    fun `givenApiDataAvailable and givenDataIsMapped whenOnRead thenResultIsAvailable`() {
        givenApiDataAvailable()
        givenDataIsMapped()
        whenOnRead()
        thenResultIsAvailable()
    }


    /*
    given
     */
    private fun givenDataIsMapped() {
        given(mockMapper.map(any())).willReturn(carEntity)
    }

    private fun givenApiDataAvailable() {
        given(mockApi.getCarData()).willReturn(
            Observable.just(apiResponse)
        )
    }

    /*
    when
     */
    private fun whenOnRead() {
        observer = subject.read().test()
    }

    /*
    then
     */

    private fun thenResultIsAvailable() = observer.assertComplete()
        .assertNoErrors()
        .assertNoTimeout()
        .assertValues(Answer.Success(carEntity))

}