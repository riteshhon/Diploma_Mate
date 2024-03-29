package com.sanjivanidsc.diplomamate.home;

import static com.sanjivanidsc.diplomamate.helper.KeyAdapter.API;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.sanjivanidsc.diplomamate.account.FragmentActivity;
import com.sanjivanidsc.diplomamate.fragments.UnavailableFragment;
import com.sanjivanidsc.diplomamate.helper.KeyAdapter;
import com.sanjivanidsc.diplomamate.R;
import com.sanjivanidsc.diplomamate.adapter.HomeSliderAdapter;
import com.sanjivanidsc.diplomamate.model.HomeSliderModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    public static String typeOfResources = "";

    Context context;
    //    Home Slider
    HomeSliderAdapter homeSliderAdapter;
    List<HomeSliderModel> homeSliderModels = new ArrayList();
    Handler homeSliderHandler = new Handler();
    Runnable homeSliderRunnable;
    ViewPager2 viewPagerHomeSlider;
    LinearLayout llShare, ll01;

    MaterialCardView cvSyllabus, cvManual, cvNotes, cvVideoLectures, cvQuestionPaper, cvAnswerPaper;

    TextView line1;

    public static boolean homeSlider = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();

        viewPagerHomeSlider = view.findViewById(R.id.viewPagerHomeSlider);
        cvSyllabus = view.findViewById(R.id.cvSyllabus);
        cvManual = view.findViewById(R.id.cvManual);
        cvNotes = view.findViewById(R.id.cvNotes);
        cvVideoLectures = view.findViewById(R.id.cvVideoLectures);
        cvQuestionPaper = view.findViewById(R.id.cvQuestionPaper);
        cvAnswerPaper = view.findViewById(R.id.cvAnswerPaper);
        line1 = view.findViewById(R.id.line1);
        llShare = view.findViewById(R.id.llShare);
        ll01 = view.findViewById(R.id.ll01);

        ll01.setVisibility(View.GONE);

        HomeOnClick();

        HomeSlider();
        return view;
    }

    private void HomeSlider() {

        //  Slider images from api
        StringRequest request = new StringRequest(Request.Method.POST, API + "homeSlider.php",
                response -> {
                    homeSliderModels.clear();
                    try {
                        JSONArray jsonArray1 = new JSONArray(response);
                        if (jsonArray1.length() != 0){
                            viewPagerHomeSlider.setVisibility(View.VISIBLE);
                            line1.setVisibility(View.VISIBLE);
                            ll01.setVisibility(View.VISIBLE);
                        } else {
                            viewPagerHomeSlider.setVisibility(View.GONE);
                            line1.setVisibility(View.GONE);
                            ll01.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            HomeSliderModel homeSliderModel = new HomeSliderModel();
                            homeSliderModel.setImageUrl(jsonObject.getString("sliderImage"));
                            homeSliderModel.setHomeUrl(jsonObject.getString("sliderUrl"));
                            homeSliderModel.setHomeTitle(jsonObject.getString("sliderTitle"));
                            homeSliderModel.setHomeDescribe(jsonObject.getString("sliderDescribe"));
                            homeSliderModels.add(homeSliderModel);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    homeSliderAdapter = new HomeSliderAdapter(homeSliderModels, context, viewPagerHomeSlider);
                    viewPagerHomeSlider.setAdapter(homeSliderAdapter);
                    viewPagerHomeSlider.setClipToPadding(false);
                    viewPagerHomeSlider.setClipChildren(false);
                    viewPagerHomeSlider.setOffscreenPageLimit(3);
                    viewPagerHomeSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

                    CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
                    compositePageTransformer.addTransformer(new MarginPageTransformer(15));
                    compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
                        @Override
                        public void transformPage(@NonNull @NotNull View page, float position) {
                            float r = 1 - Math.abs(position);
                            page.setScaleY(0.95f + r * 0.02f);
                        }
                    });
                    //   Auto Sliding Images
                    homeSliderRunnable = new Runnable() {
                        @Override
                        public void run() {
                            if (viewPagerHomeSlider.getCurrentItem() == homeSliderModels.size() - 1) {
                                viewPagerHomeSlider.setCurrentItem(0, true);
                            } else {
                                viewPagerHomeSlider.setCurrentItem(viewPagerHomeSlider.getCurrentItem() + 1, true);
                            }
                            viewPagerHomeSlider.animate();
                            viewPagerHomeSlider.requestTransform();
                        }
                    };

                    viewPagerHomeSlider.setPageTransformer(compositePageTransformer);
                    viewPagerHomeSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            homeSliderHandler.removeCallbacks(homeSliderRunnable);
                            homeSliderHandler.postDelayed(homeSliderRunnable, 4000);
                        }
                    });

                }, error -> {
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("key", KeyAdapter.KEY);
                return param;
            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        requestQueue1.add(request);
    }


    private void HomeOnClick() {

        cvSyllabus.setOnClickListener(view -> {
            FragmentActivity.fragment = "Subject";
            typeOfResources = "Syllabus";
            startActivity(new Intent(context, FragmentActivity.class));
        });
        cvManual.setOnClickListener(view -> {
            FragmentActivity.fragment = "Subject";
            typeOfResources = "Manual";
            startActivity(new Intent(context, FragmentActivity.class));
        });
        cvNotes.setOnClickListener(view -> {
//            FragmentActivity.fragment = "Subject";
//            typeOfResources = "Notes";
//            startActivity(new Intent(context, FragmentActivity.class));
            FragmentActivity.fragment = "Unavailable";
            UnavailableFragment.toolbarTitle = "Notes";
            startActivity(new Intent(context, FragmentActivity.class));
        });
        cvQuestionPaper.setOnClickListener(view -> {
            FragmentActivity.fragment = "Subject";
            typeOfResources = "QuestionPaper";
            startActivity(new Intent(context, FragmentActivity.class));
        });
        cvAnswerPaper.setOnClickListener(view -> {
            FragmentActivity.fragment = "Subject";
            typeOfResources = "AnswerPaper";
            startActivity(new Intent(context, FragmentActivity.class));
        });
        cvVideoLectures.setOnClickListener(view -> {
//            startActivity(new Intent(context, VideoLectureActivity.class));
            FragmentActivity.fragment = "Unavailable";
            UnavailableFragment.toolbarTitle = "Video Lectures";
            startActivity(new Intent(context, FragmentActivity.class));
        });

        llShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey,\nDownload this Diploma Mate app for Syllabus, Manuals, Notes, Video Lectures, Question Papers and Answer Paper. To download the app below is the link\nhttps://play.google.com/store/apps/details?id=" + getContext().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

    }

}