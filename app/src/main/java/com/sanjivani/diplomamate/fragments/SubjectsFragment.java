package com.sanjivani.diplomamate.fragments;

import static com.sanjivani.diplomamate.HomeActivity.semester;
import static com.sanjivani.diplomamate.helper.KeyAdapter.API;
import static com.sanjivani.diplomamate.helper.KeyAdapter.KEY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.adapter.AboutUsAdapter;
import com.sanjivani.diplomamate.adapter.SubjectsAdapter;
import com.sanjivani.diplomamate.helper.KeyAdapter;
import com.sanjivani.diplomamate.model.AboutUsModel;
import com.sanjivani.diplomamate.model.SubjectsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static String pdfDownload;
    CardView cvProgressBar;

    //    Subjects
    SubjectsAdapter subjectsAdapter;
    List<SubjectsModel> subjectsModels = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_subjects, container, false);

        cvProgressBar = view.findViewById(R.id.cvProgressBar);
        recyclerView = view.findViewById(R.id.recyclerView);

        callSupport();
        callSubject();

        return view;
    }

    private void callSubject() {
        cvProgressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, API+"subjects.php", response -> {
            subjectsModels.clear();
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SubjectsModel subjectsModel = new SubjectsModel();
                    subjectsModel.setSubjectName(jsonObject.getString("subjectName"));
                    subjectsModel.setSubjectDescribe(jsonObject.getString("subjectDescribe"));
                    subjectsModel.setSemester(jsonObject.getString("semester"));
                    subjectsModel.setSubjectImage(jsonObject.getString("subjectImage"));
                    subjectsModels.add(subjectsModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cvProgressBar.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            subjectsAdapter = new SubjectsAdapter(getContext(), subjectsModels);
            recyclerView.setAdapter(subjectsAdapter);

        }, error -> {

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", KEY);
                map.put("semester", semester);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    private void callSupport() {
        StringRequest request = new StringRequest(Request.Method.GET, KeyAdapter.API + "support.json", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                pdfDownload = jsonObject.getString("pdfDownload");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


}