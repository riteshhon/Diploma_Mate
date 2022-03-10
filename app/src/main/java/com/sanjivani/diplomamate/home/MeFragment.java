package com.sanjivani.diplomamate.home;

import static android.content.Context.MODE_PRIVATE;
import static com.sanjivani.diplomamate.helper.Utils.openCustomTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjivani.diplomamate.BuildConfig;
import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.SignInActivity;
import com.sanjivani.diplomamate.account.FragmentActivity;

import de.hdodenhof.circleimageview.CircleImageView;


public class MeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FirebaseAuth mAuth;
    CircleImageView userImage;
    Context context;
    TextView tvUserName, tvUserEmailId;
    LinearLayout llShare;
    Button btnLogout;
    ProgressDialog progressDialog;

    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
    MaterialCardView cvPrivacyPolicy, cvTermsCondition, cvAboutUs, cvAboutClub, cvGiveUsFeedback, cvMoreApp, cvRateUs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        context = getContext();

        customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.primary_color));

        mAuth = FirebaseAuth.getInstance();

        userImage = view.findViewById(R.id.userImage);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmailId = view.findViewById(R.id.tvUserEmailId);
        cvAboutUs = view.findViewById(R.id.cvAboutUs);
        cvAboutClub = view.findViewById(R.id.cvAboutClub);
        cvGiveUsFeedback = view.findViewById(R.id.cvGiveUsFeedback);
        cvMoreApp = view.findViewById(R.id.cvMoreApp);
        cvRateUs = view.findViewById(R.id.cvRateUs);
        llShare = view.findViewById(R.id.llShare);
        btnLogout = view.findViewById(R.id.btnLogout);

        cvPrivacyPolicy = view.findViewById(R.id.cvPrivacyPolicy);
        cvTermsCondition = view.findViewById(R.id.cvTermsCondition);

        Glide.with(context).load(mAuth.getCurrentUser().getPhotoUrl().toString()).into(userImage);
        tvUserName.setText(mAuth.getCurrentUser().getDisplayName());
        tvUserEmailId.setText(mAuth.getCurrentUser().getEmail());

        OnClick();

        return view;
    }

    private void OnClick() {

        cvPrivacyPolicy.setOnClickListener(view -> {
            String uriString = "https://androidseller.in/diploma_mate/privacy-policy";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvTermsCondition.setOnClickListener(view -> {
            String uriString = "https://androidseller.in/diploma_mate/terms-and-condition";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvAboutUs.setOnClickListener(view -> {
            FragmentActivity.fragment = "AboutUs";
            startActivity(new Intent(context, FragmentActivity.class));
        });

        cvAboutClub.setOnClickListener(view -> {
            FragmentActivity.fragment = "AboutClub";
            startActivity(new Intent(context, FragmentActivity.class));
        });

        cvGiveUsFeedback.setOnClickListener(view -> {
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sanjivanideveloperstudentclub@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Diploma Mate Feedback");
            intent.putExtra(Intent.EXTRA_TEXT, "Hello team, \n\n I am having feedback for Diploma Mate, Mentioned your message here..");
            startActivity(intent);
            try {
                startActivity(Intent.createChooser(intent, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        });

        cvMoreApp.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Android+Seller"); // missing 'http://' will cause crashed
            Intent intents = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intents);
        });

        cvRateUs.setOnClickListener(view -> {
            Uri url2 = Uri.parse("https://play.google.com/store/apps/details?id=" + getContext().getPackageName()); // missing 'http://' will cause crashed
            Intent intent2 = new Intent(Intent.ACTION_VIEW, url2);
            startActivity(intent2);
        });

        llShare.setOnClickListener(view -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey,\nDownload this Diploma Mate app for Syllabus, Manuals, Notes, Video Lectures, Question Papers and Answer Paper. To download the app below is the link\nhttps://play.google.com/store/apps/details?id=" + getContext().getPackageName());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        btnLogout.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.show();
            progressDialog.setContentView(R.layout.progressbar_loading);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    mAuth.signOut();
                    SharedPreferences sh = getContext().getSharedPreferences("BasicDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(getContext(), SignInActivity.class));
                    getActivity().finish();
                    getActivity().finishAffinity();
                }
            };
            handler.postDelayed(runnable, 2000);


        });
    }
}