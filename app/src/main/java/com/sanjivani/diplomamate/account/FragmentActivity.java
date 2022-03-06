package com.sanjivani.diplomamate.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.fragments.ManualsFragment;
import com.sanjivani.diplomamate.fragments.SubjectsFragment;
import com.sanjivani.diplomamate.fragments.SyllabusFragment;

public class FragmentActivity extends AppCompatActivity {


    public static String fragment = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        if (fragment.equals("AboutUs")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AboutUsFragment()).commit();
        } else if (fragment.equals("AboutClub")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AboutClubFragment()).commit();
        } else if (fragment.equals("Subject")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SubjectsFragment()).commit();
        } else if (fragment.equals("Syllabus")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SyllabusFragment()).commit();
        } else if (fragment.equals("Manual")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ManualsFragment()).commit();
        }


    }
}