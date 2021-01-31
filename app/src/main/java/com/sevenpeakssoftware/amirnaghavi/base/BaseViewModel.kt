package com.sevenpeakssoftware.amirnaghavi.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Base [ViewModel] class responsible to process UI event emissions using State pattern.
 * This view model is developed with the Open-Closed and Single Responsibility principle in mind.
 * @param eventHandlerManager Encapsulates the logic of holding and controlling event handlers.
 */
abstract class BaseViewModel<STATE : ViewModelState, EVENT : ViewModelEvent, PARAM : Param> (
        private val eventHandlerManager: CompositeEventHandler<STATE, PARAM>
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    /**
     * Initial state.
     */
    abstract val initState: STATE

    /**
     * The exit point of the process, each operation would be summed up at this point, resulting to a [ViewModelState]
     */
    private val _stateLiveData: MutableLiveData<STATE> = MutableLiveData()
    val stateLiveData: LiveData<STATE> get() = _stateLiveData

    /**
     * The starting point of the operation, each [ViewModelEvent] would start it's journey from this point and
     * [EventHandler]s would be notified for each event emission.
     */
    private val eventBridge: PublishSubject<Pair<EVENT, PARAM>> = PublishSubject.create()

    init {
        /**
         * Wires up view model event emissions to the composite event handler as the event handler manager.
         */
        eventBridge.map {
            eventHandlerManager.handleEvent(it.first, it.second, initState).subscribe { state ->
                _stateLiveData.postValue(state)
            }
        }.doOnSubscribe {
            compositeDisposable.add(it)
        }.subscribe()
    }

    /**
     * Handles incoming events from UI.
     */
    fun handleEvent(event: EVENT, param: PARAM) = eventBridge.onNext(Pair(event, param))


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

abstract class ViewModelState {
    open var baseState: BaseState = BaseState()
}

interface ViewModelEvent {
    val ID: String
}



