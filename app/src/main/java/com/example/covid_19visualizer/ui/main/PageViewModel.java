package com.example.covid_19visualizer.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            // return "Hello world from section: " + input;

            // *************** ADDED BY HARRY ***************
            switch(input) {
                case 1: // Map
                    return "Map will go here!";

                case 2:
                    return "General info/headlines will go here!";

                case 3:
                    return "News go here"; //added by Federico
                default:
                    return "";
            }
            // *************** ADDED BY HARRY ***************
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}