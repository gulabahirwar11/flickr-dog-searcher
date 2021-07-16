package com.example.myapplication.presention

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.myapplication.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FlickrPhotoActivityTest {

    @Rule
    @JvmField
    var flickrPhotoActivityActivityTestRule = ActivityTestRule(FlickrPhotoActivity::class.java)

   // recylcerview comes into view
    @Test
    fun test_isRecyclerviewyDisplayAtActivityOpen() {
       onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isSearchIconDisplayAtActivityOpen() {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recyclerviewScrolling() {
        val recyclerView : RecyclerView = flickrPhotoActivityActivityTestRule
                .activity.findViewById(R.id.recycler_view)
       val itemCount =  recyclerView.adapter?.itemCount
        if (itemCount != null) {
            Espresso.onView(withId(R.id.recycler_view)).perform(RecyclerViewActions
                    .scrollToPosition<RecyclerView.ViewHolder>(itemCount.minus(1)))
        }
    }
}