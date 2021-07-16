package com.example.myapplication.data

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class FlickResponseEntity(@SerializedName("photos") val flickrDataEntity : FlickrDataEntity)

data class FlickrDataEntity(@SerializedName("photo") val flickEntityList : ArrayList<FlickrEntity>,
                      @SerializedName("pages") val pages : Int)

data class FlickrEntity (@SerializedName("id")  val id : String,
                         @SerializedName("owner") val onwer : String,
                         @SerializedName("secret") val serect : String,
                         @SerializedName("server") val server : String,
                         @SerializedName("farm") val farm : String,
                         @SerializedName("title") val title : String)