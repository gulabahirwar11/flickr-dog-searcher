package com.example.myapplication.domain.utils

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtil {
    fun <T> applySingleSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer { upstream: Single<T> ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}