package com.example.myapplication.domain.events


class Event<ET, T> constructor(private val eventType: ET,
                               private val data: T? = null) {
    private var isDataHandled = false
    private var isEventHandled = false

    val dataIfNotHandled: T?
        get() = if (isDataHandled) null else {
            isDataHandled = true
            data
        }

    val eventIfNotHandled: ET?
        get() = if (isEventHandled) null else {
            isEventHandled = true
            eventType
        }
}