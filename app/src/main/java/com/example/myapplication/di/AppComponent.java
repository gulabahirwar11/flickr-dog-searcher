package com.example.myapplication.di;

import com.example.myapplication.presention.FlickrPhotoActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(FlickrPhotoActivity activity);
}
