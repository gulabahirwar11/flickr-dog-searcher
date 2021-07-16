package com.example.myapplication.presention.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.events.Event
import com.example.myapplication.domain.events.EventType
import com.example.myapplication.domain.datasouce.FlickrDataSource
import com.example.myapplication.domain.utils.RxUtil
import io.reactivex.disposables.CompositeDisposable

class FlickrViewmodel(private val dataSource: FlickrDataSource) : ViewModel() {
    var totalPages = 0

    private val disposables = CompositeDisposable()
    val eventBus = MutableLiveData<Event<EventType, Any>>()

    fun getFetchFlickrItems(searchText: String, page: Int) {
        val disposable = dataSource.getFetchFlickrItems(searchText, page)
                .compose(RxUtil.applySingleSchedulers())
                .subscribe({
                    if (page > 2) {
                        eventBus.postValue(Event(EventType.LOAD_MORE, it))
                    } else {
                        eventBus.postValue(Event(EventType.SUCCESS, it))
                    }
                }, {
                    eventBus.postValue(Event(EventType.ERROR))
                })
        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}