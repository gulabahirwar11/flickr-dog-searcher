package com.example.myapplication.data

import com.example.myapplication.data.mapper.FilckrMapper
import com.example.myapplication.data.network.ApiInterface
import com.example.myapplication.domain.entity.Flickr
import com.example.myapplication.domain.datasouce.FlickrDataSource
import com.example.myapplication.domain.entity.FlickrData
import io.reactivex.Single
import java.util.ArrayList

private val METHOD  = "method"
private val API_KEY = "api_key"
private val TEXT = "text"
private val FORMAT = "format"
private val NO_JSON_CALLBACK = "nojsoncallback"
private val PER_PAGE = "per_page"
private val PAGE = "page"
private val TAGS = "tags"

class FlickrRepository(private val apiInterface : ApiInterface) : FlickrDataSource {
    override fun getFetchFlickrItems(searchText : String, page: Int): Single<FlickrData> {
        val queryMap = LinkedHashMap<String, String>()
        queryMap[METHOD] = "flickr.photos.search"
        queryMap[API_KEY] = "062a6c0c49e4de1d78497d13a7dbb360"
        queryMap[TEXT] = searchText
        queryMap[FORMAT] = "json"
        queryMap[NO_JSON_CALLBACK] = "1"
        queryMap[PER_PAGE] = "10"
        queryMap[TAGS] = "dog"
        queryMap[PAGE] = page.toString()

        return apiInterface.fetchFlickItems(queryMap).map { FilckrMapper.transform(it) }
    }
}
