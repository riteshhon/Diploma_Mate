package com.sanjivani.diplomamate;

import static com.sanjivani.diplomamate.KeyAdapter.API;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sanjivani.diplomamate.adapter.HomeSliderAdapter;
import com.sanjivani.diplomamate.model.HomeSliderModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    public static String log, department, year, semester;

    //    Home Slider
    HomeSliderAdapter homeSliderAdapter;
    List<HomeSliderModel> homeSliderModels = new ArrayList();
    Handler homeSliderHandler = new Handler();
    Runnable homeSliderRunnable;
    ViewPager2 viewPagerHomeSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sh = getSharedPreferences("BasicDetails", MODE_PRIVATE);
        department = sh.getString("department", "");
        year = sh.getString("year", "");
        semester = sh.getString("semester", "");


        viewPagerHomeSlider = findViewById(R.id.viewPagerHomeSlider);

        HomeSlider();

    }


    private void HomeSlider() {

        //  Slider images from api
        StringRequest request = new StringRequest(Request.Method.POST, API + "homeSlider.php",
                response -> {
                    homeSliderModels.clear();
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
                    homeSliderAdapter = new HomeSliderAdapter(homeSliderModels, HomeActivity.this, viewPagerHomeSlider);
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
                            page.setScaleY(0.95f + r * 0.05f);
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
                            homeSliderHandler.postDelayed(homeSliderRunnable, 3000);
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
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(request);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();
    }

}