package com.example.myapplication.presention

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.di.DaggerAppComponent
import com.example.myapplication.domain.ViewmodelFactory
import com.example.myapplication.domain.datasouce.FlickrDataSource
import com.example.myapplication.domain.entity.Flickr
import com.example.myapplication.domain.entity.FlickrData
import com.example.myapplication.domain.events.EventType
import com.example.myapplication.presention.adapter.FlickrRecyclerAdapter
import com.example.myapplication.presention.viewmodel.FlickrViewmodel
import kotlinx.android.synthetic.main.flickr_activity.*
import javax.inject.Inject

class FlickrPhotoActivity : AppCompatActivity() {
    @Inject
    lateinit var flickrDataSource: FlickrDataSource
    private lateinit var flickrViewmodel: FlickrViewmodel

    private val flickrAdapter = FlickrRecyclerAdapter()

    private val columnNumber = 2
    private var searchQuery = ""

    private val LIST_STATE = "list_state"
    private val BUNDLE_RECYCLER_LAYOUT = "recycler_layout"

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.builder().build().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flickr_activity)
        flickrViewmodel = ViewModelProvider(this, ViewmodelFactory(flickrDataSource))
                .get(FlickrViewmodel::class.java)

        if (savedInstanceState != null) {
            displayData()
        } else {
            initView()
        }

        observeUIEvents(flickrViewmodel)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)
                        && flickrViewmodel.page <= flickrViewmodel.totalPages) {
                    flickrViewmodel.page += 1
                    flickrViewmodel.getFetchFlickrItems(searchQuery)
                }
            }
        })
    }

    private fun initView() {
        recycler_view.layoutManager = GridLayoutManager(this, columnNumber)
        recycler_view.adapter = flickrAdapter

        flickrViewmodel.getFetchFlickrItems(searchQuery)
    }

    private fun displayData() {
        recycler_view.layoutManager = GridLayoutManager(this, columnNumber)
        if (flickrViewmodel.savedRecyclerLayoutState != null) {
            (recycler_view.layoutManager as GridLayoutManager)
                    .onRestoreInstanceState(flickrViewmodel.savedRecyclerLayoutState)
        }
        recycler_view.adapter = flickrAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val menuItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                flickrViewmodel.page = 2
                searchQuery = query ?: ""
                flickrViewmodel.getFetchFlickrItems(searchQuery)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                flickrViewmodel.page = 2
                searchQuery = newText ?: ""
                flickrViewmodel.getFetchFlickrItems(searchQuery)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putParcelableArrayList(LIST_STATE, flickrAdapter.flickrList)
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recycler_view.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        flickrAdapter.flickrList  = savedInstanceState.getParcelableArrayList<Flickr>(LIST_STATE) as ArrayList<Flickr>
        flickrViewmodel.savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT)
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun observeUIEvents(flickrViewmodel: FlickrViewmodel) {
        flickrViewmodel.eventBus.observe(this, Observer {
            when (it.eventIfNotHandled ?: return@Observer) {
                EventType.SUCCESS -> {
                    val data = it.dataIfNotHandled as FlickrData
                    flickrViewmodel.totalPages = data.pages
                    flickrAdapter.setItems(data.arrayList)
                }
                EventType.LOAD_MORE -> {
                    val data = it.dataIfNotHandled as FlickrData
                    flickrViewmodel.totalPages = data.pages
                    flickrAdapter.addItems(data.arrayList)
                }
                EventType.ERROR -> {
                    Toast.makeText(this,
                            getString(R.string.default_error_message), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}