package com.example.myapplication.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flickr (val id : String,
                   val onwer : String,
                   val serect : String,
                   val server : String,
                   val farm : String,
                   val title : String) : Parcelable

data class FlickrData(val pages : Int,
                      val arrayList: ArrayList<Flickr>)