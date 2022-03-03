package com.sanjivani.diplomamate.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.model.HomeSliderModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.ViewHolder> {

    List<HomeSliderModel> homeSliderModels;
    Context context;
    ViewPager2 viewPager2;

    public HomeSliderAdapter(List<HomeSliderModel> homeSliderModels, Context context, ViewPager2 viewPager2) {
        this.homeSliderModels = homeSliderModels;
        this.context = context;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homeslider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(homeSliderModels.get(position).getImageUrl()).into(holder.img_Slider);
        holder.tv_HomeTitle.setText(homeSliderModels.get(position).getHomeTitle());
        holder.tv_SliderDescribe.setText(homeSliderModels.get(position).getHomeDescribe());

        holder.LL_HomeSlider.setOnClickListener(v -> {
            String urlString = homeSliderModels.get(position).getHomeUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent.setPackage(null);
                context.startActivity(intent);
            }
        });

        if (homeSliderModels.get(position).getHomeDescribe() != null){
            holder.tv_SliderDescribe.setVisibility(View.VISIBLE);
        } else {
            holder.tv_SliderDescribe.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return homeSliderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LL_HomeSlider;
        ImageView img_Slider;
        TextView tv_HomeTitle, tv_SliderDescribe;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            LL_HomeSlider = itemView.findViewById(R.id.LL_HomeSlider);
            img_Slider = itemView.findViewById(R.id.img_Slider);
            tv_HomeTitle = itemView.findViewById(R.id.tv_HomeTitle);
            tv_SliderDescribe = itemView.findViewById(R.id.tv_SliderDescribe);
        }
    }


}
