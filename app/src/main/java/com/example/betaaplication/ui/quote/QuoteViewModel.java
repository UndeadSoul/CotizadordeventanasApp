package com.example.betaaplication.ui.quote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuoteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QuoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Cotización");
    }

    public LiveData<String> getText() {
        return mText;
    }
}