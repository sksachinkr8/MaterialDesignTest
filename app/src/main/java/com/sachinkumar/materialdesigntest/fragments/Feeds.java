package com.sachinkumar.materialdesigntest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sachinkumar.materialdesigntest.R;

/**
 * Created by sachinkumar on 09/11/15.
 */
public class Feeds extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

}
