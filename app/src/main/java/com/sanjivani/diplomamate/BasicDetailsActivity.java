package com.sanjivani.diplomamate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class BasicDetailsActivity extends AppCompatActivity {


    MaterialSpinner spinnerDepartment, spinnerYear, spinnerSemester;
    MaterialCardView cv_Submit;

    private static String department, year, semester;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_details);

        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerSemester = findViewById(R.id.spinnerSemester);
        cv_Submit = findViewById(R.id.cv_Submit);

        allBasicDetails();

    }

    private void allBasicDetails() {

        department = "Computer Technology";
        year = "First Year";
        semester = "1st Semester";

        spinnerDepartment.setItems("Computer Technology");
        //Spinner Operator  OnClickListener
        spinnerDepartment.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        department = item.toString();
                        return;
                }
                return;
            }
        });

        spinnerYear.setItems("First Year", "Second Year", "Third Year");
        //Spinner Operator  OnClickListener
        spinnerYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        year= "First Year";
                        spinnerSemester.setItems("1st Semester", "2nd Semester");
                        return;
                    case 1:
                        year= "Second Year";
                        spinnerSemester.setItems("3rd Semester", "4th Semester");
                        break;
                    case 2:
                        year= "Third Year";
                        spinnerSemester.setItems("5th Semester", "6th Semester");
                        break;
                }
                return;
            }
        });


        spinnerSemester.setItems("1st Semester", "2nd Semester");
        //Spinner Operator  OnClickListener
        spinnerSemester.setOnItemSelectedListener((view, position, id, item) -> {
            switch (position) {
                case 0:
                    semester = item.toString();
                    return;
                case 1:
                    semester = item.toString();
                    break;
            }
            return;
        });


        cv_Submit.setOnClickListener(view -> {
            callAdd(semester, department, year);
        });

        SharedPreferences sh = getSharedPreferences("BasicDetails", MODE_PRIVATE);
        String logCheck = sh.getString("log", "");

        if (logCheck.equals("true")){
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {

        }


    }

    private void callAdd(String semester, String department, String year) {
        SharedPreferences sharedPreferences = getSharedPreferences("BasicDetails",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("log", "true");
        myEdit.putString("department", department);
        myEdit.putString("year", year);
        myEdit.putString("semester", semester);
        myEdit.commit();

        startActivity(new Intent(this, HomeActivity.class));
        finish();

    }
}