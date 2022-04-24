package com.sanjivani.diplomamate.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanjivani.diplomamate.R;

public class UnavailableFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView tvToolBarTitle;
    public static String toolbarTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_unavailable, container, false);

        tvToolBarTitle = view.findViewById(R.id.tvToolBarTitle);
        tvToolBarTitle.setText(toolbarTitle);


        return view;
    }
}