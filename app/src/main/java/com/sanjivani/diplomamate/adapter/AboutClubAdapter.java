package com.sanjivani.diplomamate.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.sanjivani.diplomamate.R;
import com.sanjivani.diplomamate.model.AboutClubModel;
import com.sanjivani.diplomamate.model.AboutUsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutClubAdapter extends RecyclerView.Adapter<AboutClubAdapter.ViewHolder> {

    Context context;
    List<AboutClubModel> list;

    public AboutClubAdapter(Context context, List<AboutClubModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_aboutclub, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.ivTeamMember);
        holder.tvTeamMemberName.setText(list.get(position).getName());
        holder.tvTeamMemberInfo.setText(list.get(position).getDescribeMember());

        holder.imgLinkedIn.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getLinkedInUrl())));
        });
        holder.imgTwitter.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getTwitterUrl())));
        });
        holder.imgGithub.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getGithubUrl())));
        });

        if (!list.get(position).getLinkedInUrl().equals("")){
            holder.imgLinkedIn.setVisibility(View.VISIBLE);
        } else {
            holder.imgLinkedIn.setVisibility(View.GONE);
        }
        if (!list.get(position).getTwitterUrl().equals("")){
            holder.imgTwitter.setVisibility(View.VISIBLE);
        } else {
            holder.imgTwitter.setVisibility(View.GONE);
        }
        if (!list.get(position).getGithubUrl().equals("")){
            holder.imgGithub.setVisibility(View.VISIBLE);
        } else {
            holder.imgGithub.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivTeamMember;
        TextView tvTeamMemberName, tvTeamMemberInfo;
        ImageView imgLinkedIn, imgTwitter, imgGithub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTeamMember = itemView.findViewById(R.id.ivTeamMember);
            tvTeamMemberName = itemView.findViewById(R.id.tvTeamMemberName);
            tvTeamMemberInfo = itemView.findViewById(R.id.tvTeamMemberInfo);
            imgLinkedIn = itemView.findViewById(R.id.imgLinkedIn);
            imgTwitter = itemView.findViewById(R.id.imgTwitter);
            imgGithub = itemView.findViewById(R.id.imgGithub);

        }
    }

}
