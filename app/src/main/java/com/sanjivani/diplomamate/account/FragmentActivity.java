package com.sanjivani.diplomamate.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.sanjivani.diplomamate.R;

public class FragmentActivity extends AppCompatActivity {


    public static String fragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (fragment.equals("AboutUs")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AboutUsFragment()).commit();
        }


    }
}