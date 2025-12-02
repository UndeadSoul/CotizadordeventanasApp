package com.example.betaaplication.ui.projects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProjectsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Projects fragment");
    }

    public LiveData<String> getText() {return mText;}

    // TODO: Implement the ViewModel
}