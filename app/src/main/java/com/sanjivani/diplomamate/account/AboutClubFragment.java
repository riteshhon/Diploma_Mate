package com.sanjivani.diplomamate.account;

import static com.sanjivani.diplomamate.helper.KeyAdapter.API;
import static com.sanjivani.diplomamate.helper.KeyAdapter.KEY;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.adapter.AboutClubAdapter;
import com.sanjivani.diplomamate.adapter.AboutUsAdapter;
import com.sanjivani.diplomamate.helper.KeyAdapter;
import com.sanjivani.diplomamate.model.AboutClubModel;
import com.sanjivani.diplomamate.model.AboutUsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutClubFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth mAuth;
    Context context;
    CardView cvProgressBar;

    CircleImageView ivHodSir, ivInchargeSir;
    MaterialCardView cvHODLinkedIn, cvInchargeSirLinkedIn;
    String hodSirPic, hodSirLinkedIn, inchargePic, inchargeLinkedIn;

    //    AboutUs
    AboutClubAdapter aboutClubAdapter;
    List<AboutClubModel> aboutClubModels = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_club, container, false);

        context = getContext();

        recyclerView = view.findViewById(R.id.recyclerView);
        cvProgressBar = view.findViewById(R.id.cvProgressBar);
        ivHodSir = view.findViewById(R.id.ivHodSir);
        ivInchargeSir = view.findViewById(R.id.ivInchargeSir);
        cvHODLinkedIn = view.findViewById(R.id.cvHODLinkedIn);
        cvInchargeSirLinkedIn = view.findViewById(R.id.cvInchargeSirLinkedIn);

        callSupport();

        mAuth = FirebaseAuth.getInstance();

        callClubMembers();


        return view;
    }

    private void callSupport() {
        StringRequest request = new StringRequest(Request.Method.GET, KeyAdapter.API + "support.json", response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                hodSirPic = jsonObject.getString("hodSirPic");
                hodSirLinkedIn = jsonObject.getString("hodSirLinkedIn");
                inchargePic = jsonObject.getString("inchargePic");
                inchargeLinkedIn = jsonObject.getString("inchargeLinkedIn");

                Glide.with(context).load(hodSirPic).into(ivHodSir);
                Glide.with(context).load(inchargePic).into(ivInchargeSir);

                cvHODLinkedIn.setOnClickListener(view1 -> {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hodSirLinkedIn)));
                });
                cvInchargeSirLinkedIn.setOnClickListener(view1 -> {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(inchargeLinkedIn)));
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void callClubMembers() {
        cvProgressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, API+"aboutClub.php", response -> {
            aboutClubModels.clear();
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    AboutClubModel aboutClubModel = new AboutClubModel();
                    aboutClubModel.setId(jsonObject.getString("id"));
                    aboutClubModel.setImageUrl(jsonObject.getString("imageUrl"));
                    aboutClubModel.setName(jsonObject.getString("name"));
                    aboutClubModel.setDescribeMember(jsonObject.getString("describeMember"));
                    aboutClubModel.setLinkedInUrl(jsonObject.getString("linkedInUrl"));
                    aboutClubModel.setTwitterUrl(jsonObject.getString("twitterUrl"));
                    aboutClubModel.setGithubUrl(jsonObject.getString("githubUrl"));
                    aboutClubModels.add(aboutClubModel);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            cvProgressBar.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            aboutClubAdapter = new AboutClubAdapter(context, aboutClubModels);
            recyclerView.setAdapter(aboutClubAdapter);

        }, error -> {

        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> map = new HashMap<>();
                map.put("key", KEY);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}