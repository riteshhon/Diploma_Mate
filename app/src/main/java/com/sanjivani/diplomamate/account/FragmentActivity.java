package com.sanjivani.diplomamate.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.fragments.AnswerPaperFragment;
import com.sanjivani.diplomamate.fragments.ManualsFragment;
import com.sanjivani.diplomamate.fragments.NotesFragment;
import com.sanjivani.diplomamate.fragments.QuestionPaperFragment;
import com.sanjivani.diplomamate.fragments.SubjectsFragment;
import com.sanjivani.diplomamate.fragments.SyllabusFragment;
import com.sanjivani.diplomamate.fragments.UnavailableFragment;

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
        } else if (fragment.equals("Notes")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new NotesFragment()).commit();
        } else if (fragment.equals("QuestionPaper")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new QuestionPaperFragment()).commit();
        } else if (fragment.equals("AnswerPaper")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AnswerPaperFragment()).commit();
        } else if (fragment.equals("Unavailable")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new UnavailableFragment()).commit();
        }


    }
}