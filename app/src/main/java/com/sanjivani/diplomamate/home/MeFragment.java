package com.sanjivani.diplomamate.home;

import static com.sanjivani.diplomamate.helper.Utils.openCustomTab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.sanjivani.diplomamate.R;
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

    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
    MaterialCardView cvPrivacyPolicy, cvTermsCondition, cvAboutUs;

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
            String uriString = "https://google.com";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvTermsCondition.setOnClickListener(view -> {
            String uriString = "https://google.com";

            openCustomTab(context, customIntent.build(), Uri.parse(uriString));
        });

        cvAboutUs.setOnClickListener(view -> {
            FragmentActivity.fragment = "AboutUs";
            startActivity(new Intent(context, FragmentActivity.class));
        });

    }
}